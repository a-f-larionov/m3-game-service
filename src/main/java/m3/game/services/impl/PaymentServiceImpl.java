package m3.game.services.impl;

import lombok.RequiredArgsConstructor;
import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.game.dto.vk.rs.VKResponseDoOrderErrorRsDto;
import m3.game.dto.vk.rs.VKResponseDoOrderSuccessRsDto;
import m3.game.mappers.PaymentMapper;
import m3.game.services.PaymentService;
import m3.game.services.StuffService;
import m3.lib.dto.ProductDto;
import m3.lib.entities.PaymentEntity;
import m3.lib.enums.ClientLogLevels;
import m3.lib.enums.SocNetType;
import m3.lib.kafka.sender.CommonSender;
import m3.lib.repositories.PaymentRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.store.ShopStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static m3.lib.enums.StatisticEnum.ID_BUY_VK_MONEY;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    //@todo thread safe counter!
    private static Long lastTid = 1L;

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final StuffService stuffService;
    private final CommonSender commonSender;
    @Value("${socnet.vk.appId}")
    private Long vkAppId;
    @Value("${socnet.vk.secretKey}")
    private String vkSecretKey;

    private static final VKResponseDoOrderErrorRsDto vkErrorCommon = VKResponseDoOrderErrorRsDto
            .builder()
            .errorCode(1L)
            .errorMsg("общая ошибка")
            .crtitcal(false)
            .build();

    private static final VKResponseDoOrderErrorRsDto vkErrorItemPriceNotFound = VKResponseDoOrderErrorRsDto
            .builder()
            .errorCode(1L)
            .errorMsg("нет такого товара")
            .crtitcal(true)
            .build();

    private static final VKResponseDoOrderErrorRsDto vkErrorSign = VKResponseDoOrderErrorRsDto
            .builder()
            .errorCode(10L)
            .errorMsg("несовпадение вычисленной и переданной подписи.")
            .crtitcal(true)
            .build();

    @Override
    public DoOrderChangeAnswerRsDto standaloneBuy(Long socNetUserId, Long itemPrice, Long extOrderId) {
        var tid = lastTid++;

        commonSender.log(null,
                format("Standalone pay request incoming: \r\n" +
                                "socNetUserId: %s extOrderId: %s itemPrice: %s",
                        socNetUserId, extOrderId, itemPrice),
                ClientLogLevels.INFO, true);

        return doOrderChange(tid, SocNetType.Standalone, socNetUserId, itemPrice, extOrderId);
    }

    /**
     * @see <a href="https://vk.com/dev/payments_callbacks?f=3.%20%D0%9F%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D0%BA%D0%B0%20%D0%BF%D0%BE%D0%B4%D0%BF%D0%B8%D1%81%D0%B8">VK dev doc</a>
     */
    @Override
    public DoOrderChangeAnswerRsDto vkBuy(Long appId, Long socNetUserId, String sig,
                                          Long itemPrice, Long orderId,
                                          String notificationType, String status) {
        Long tid = lastTid++;
        //@todo how do it by one string from getParams\or may be Post?
        Map<String, String> params = Map.of(
                "app_id", appId.toString(),
                "receiver_id", socNetUserId.toString(),
                "sig", sig,
                "order_id", orderId.toString(),
                "item_price", itemPrice.toString(),
                "notification_type", notificationType,
                "status", status);

        commonSender.log(null,
                format("Standalone pay request incoming: \r\n" +
                                "socNetUserId: %s orderId: %s itemPrice: %s",
                        socNetUserId, orderId, itemPrice),
                ClientLogLevels.INFO, true);

        //@todo write to file
        //@todo validate, every field is not null
        //@todo return vkErrorCommon on validate error
        //@todo convert to DTO, and DTO on @GetMapping request!

        if (!appId.equals(vkAppId)) {
            commonSender.log(null, "Wrong appId. " + appId, ClientLogLevels.WARN, true);
            return buildVKErrorCommon(tid);
        }

        if (!(notificationType.equals("order_status_change") || notificationType.equals("order_status_change_test"))) {
            commonSender.log(null, "Wrong notification type. " + status, ClientLogLevels.WARN, true);
            return buildVKErrorCommon(tid);
        }

        if (!status.equals("chargeable")) {
            commonSender.log(null, "Wrong status. " + status, ClientLogLevels.WARN, true);
            return buildVKErrorCommon(tid);
        }

        if (!checkVKSign(sig, params)) {
            commonSender.log(null, "Wrong signature. " + sig + " expected:" + calcVKSign(params), ClientLogLevels.WARN, true);
            return DoOrderChangeAnswerRsDto.builder()
                    .response(vkErrorSign)
                    .tid(tid)
                    .build();
        }

        return doOrderChange(tid, SocNetType.VK, socNetUserId, itemPrice, orderId);
    }

    @Override
    public DoOrderChangeAnswerRsDto doOrderChange(Long reqId, SocNetType socNetType, Long socNetUserId, Long itemPrice, Long extOrderId) {

        var userOptional = userRepository.findBySocNetTypeIdAndSocNetUserId(socNetType.getId(), socNetUserId);
        if (userOptional.isEmpty()) {
            commonSender.log(null, format("Order failed. Not user found.:\r\n" +
                            "reqId: %s, userId: %s, orderId: %s, appOrderId: %s", reqId, "-", extOrderId, "-"),
                    ClientLogLevels.INFO, true);
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(reqId)
                    .response(vkErrorCommon)
                    .build();
        }
        var user = userOptional.get();

        if (!ShopStore.goldProductByPriceExists(itemPrice)) {
            commonSender.log(null, format("Order failed. Not product found.:\r\n" +
                            "reqId: %s, userId: %s, orderId: %s, appOrderId: %s", reqId, user.getId(), extOrderId, "-"),
                    ClientLogLevels.INFO, true);
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(reqId)
                    .response(vkErrorItemPriceNotFound)
                    .build();
        }

        ProductDto product = ShopStore.getGoldProductByPrice(itemPrice);

        Optional<PaymentEntity> payment = paymentRepository.findByOrderId(extOrderId);
        if (payment.isPresent()) {
            commonSender.log(null, format("Order failed. Not user found.:\r\n" +
                            "reqId: %s, userId: %s, orderId: %s, appOrderId: %s", reqId, user.getId(), extOrderId, payment.get().getId()),
                    ClientLogLevels.INFO, true);
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(reqId)
                    .response(vkErrorCommon)
                    .build();
        }

        //@todo transaction analyzing. payment saved, but user stuff throw exception!
        PaymentEntity entity = paymentMapper.toEntity(System.currentTimeMillis() / 1000L, user.getId(), extOrderId, itemPrice);
        PaymentEntity newOrder = paymentRepository.save(entity);

        stuffService.giveAGold(user.getId(), product.getQuantity());

        stuffService.sendToUser(user.getId());

        commonSender.statistic(user.getId(), ID_BUY_VK_MONEY, newOrder.getId().toString(), itemPrice.toString());
        //@todo write to file necessary
        commonSender.log(user.getId(), format("Order successed:\r\n" +
                        "reqId: %s, userId: %s, orderId: %s, appOrderId: %s", reqId, user.getId(), extOrderId, newOrder.getId()),
                ClientLogLevels.INFO, true);

        return DoOrderChangeAnswerRsDto.builder()
                .tid(reqId)
                .response(VKResponseDoOrderSuccessRsDto.builder()
                        .orderId(extOrderId)
                        .appOrderId(newOrder.getId())
                        .build())
                .build();
    }

    private boolean checkVKSign(String sig, Map<String, String> params) {
        return sig.equals(calcVKSign(params));
    }

    private String calcVKSign(Map<String, String> params) {
        return DigestUtils.md5DigestAsHex(
                (params.keySet()
                        .stream()
                        .filter(key -> !key.equals("sig"))
                        .sorted()
                        .map(k -> k + "=" + params.get(k))
                        .collect(Collectors.joining(""))
                        + vkSecretKey)
                        .getBytes());
    }

    private DoOrderChangeAnswerRsDto buildVKErrorCommon(Long tid) {
        return DoOrderChangeAnswerRsDto.builder()
                .response(vkErrorCommon)
                .tid(tid)
                .build();
    }
}
