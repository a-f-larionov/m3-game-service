package m3.gameplay.services.impl;

import lombok.RequiredArgsConstructor;
import m3.gameplay.dto.MapDto;
import m3.gameplay.dto.rs.*;
import m3.gameplay.dto.vk.rs.VKResponseDoOrderErrorRsDto;
import m3.gameplay.dto.vk.rs.VKResponseDoOrderSuccessRsDto;
import m3.lib.kafka.sender.CommonSender;
import m3.gameplay.mappers.MapMapper;
import m3.gameplay.mappers.PaymentMapper;
import m3.gameplay.mappers.ScoreMapper;
import m3.gameplay.mappers.StuffMapper;
import m3.gameplay.services.ChestsService;
import m3.gameplay.services.MapService;
import m3.gameplay.services.PointsService;
import m3.gameplay.services.StuffService;
import m3.gameplay.store.MapStore;
import m3.lib.dto.ProductDto;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.entities.PaymentEntity;
import m3.lib.entities.UserEntity;
import m3.lib.entities.UserPointEntity;
import m3.lib.entities.UserStuffEntity;
import m3.lib.enums.ClientLogLevels;
import m3.lib.enums.ObjectEnum;
import m3.lib.enums.SocNetType;
import m3.lib.enums.StatisticEnum;
import m3.lib.helpers.UserHelper;
import m3.lib.repositories.PaymentRepository;
import m3.lib.repositories.UserPointRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
import m3.lib.store.ShopStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static m3.lib.enums.StatisticEnum.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final PointsService pointsService;
    private final ChestsService chestsService;
    private final StuffService stuffService;
    private final MapMapper mapMapper;
    private final StuffMapper stuffMapper;
    private final ScoreMapper scoreMapper;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;
    private final UserStuffRepository userStuffRepository;
    private final PaymentRepository paymentRepository;
    private final CommonSender commonSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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
    public boolean existsMap(Long mapId) {
        return MapStore.maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapDto getById(Long mapId) {
        return MapStore.maps.stream()
                .filter(m -> m.getId().equals(mapId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Map not found"));
    }

    @Override
    public GotMapInfoRsDto getMapInfo(Long userId, Long mapId) {
        // @todo user set to first positions
        var map = getById(mapId);
        var points = pointsService.getPointsByMapId(mapId);
        return mapMapper.entitiesToRsDto(
                userId,
                mapId,
                map,
                points
        );
    }

    @Override
    public GotScoresRsDto getScores(Long userId, List<Long> pids, List<Long> uids) {
        List<UserPointEntity> scores = userPointRepository.getScores(pids, uids);
        return scoreMapper.toDto(userId, scores);
    }

    @Override
    public void onFinish(Long userId, Long pointId, Long score, Long chestId) {
        // @todo transactional one for all method
        getUser(userId);

        updateUserPoint(userId, pointId, score);
        nextPointUp(userId, pointId);
        giveChest(userId, chestId);
    }

    @Override
    public GotPointTopScoreRsDto getPointTopScore(Long userId, Long pointId, Long score, List<Long> fids) {

        List<UserPointEntity> topScore = userPointRepository.getTopScore(pointId, fids);
        Long userPosition = userPointRepository.getTopScoreUserPosition(score, pointId, fids, userId);

        return scoreMapper.toDto(
                userId,
                getPlace1Uid(topScore, 0),
                getPlace1Uid(topScore, 1),
                getPlace1Uid(topScore, 2),
                userPosition,
                pointId
        );
    }

    private static Long getPlace1Uid(List<UserPointEntity> topScore, int index) {
        return topScore.size() > index ? topScore.get(index).getId().getUserId() : null;
    }

    @Override
    public GotStuffRsDto getUserStuffRsDto(Long userId) {
        Optional<UserStuffEntity> userStuff = userStuffRepository.findById(userId);
        if (userStuff.isEmpty()) {
            userStuffRepository.creatUserStuff(userId);
            userStuff = userStuffRepository.findById(userId);
        }
        return stuffMapper.entityToDto(userStuff.get());
    }

    private void updateUserPoint(Long userId, Long pointId, Long score) {
        commonSender.statistic(userId, StatisticEnum.ID_FINISH_PLAY, pointId.toString(), score.toString());
        userPointRepository.updateUserPoint(userId, pointId, score);
        //     TopScoreCache.flush(cntx.user.id, pointId);
    }

    private void nextPointUp(Long userId, Long pointId) {
        userRepository.nextPointUp(userId, pointId + 1);
        commonSender.statistic(userId, StatisticEnum.ID_LEVEL_UP, String.valueOf(pointId + 1));
    }

    public void giveChest(Long userId, Long chestId) {
        // zero means - no chest to open
        if (chestId.equals(0L)) return;

        var chest = chestsService.getById(chestId);

        chest.getPrizes().forEach(prize -> {
            switch (prize.getId()) {
                case STUFF_HUMMER -> stuffService.giveAHummer(userId, prize.getCount());
                case STUFF_LIGHTNING -> stuffService.giveALightning(userId, prize.getCount());
                case STUFF_SHUFFLE -> stuffService.giveAShuffle(userId, prize.getCount());
                case STUFF_GOLD -> stuffService.giveAGold(userId, prize.getCount());
            }
        });

        sendStuffToUser(userId);

        //@Todo transaction control!
        commonSender.statistic(userId, StatisticEnum.ID_OPEN_CHEST, chestId.toString());
    }

    @Override
    public GotStuffRsDto spendCoinsForTurns(Long userId) {
        var product = ShopStore.turnsUp;
        var userStuff = getUserStuff(userId);

        decrementStuff(userStuff, product.getPriceGold(), ObjectEnum.STUFF_GOLD);

        userStuffRepository.save(userStuff);

        commonSender.statistic(userId, StatisticEnum.ID_BUY_TURNS);
        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public UpdateUserInfoRsDto buyHealth(Long userId, Long index) {
        var userEntity = getUser(userId);
        var userStuff = getUserStuff(userId);

        if (UserHelper.getHealths(userEntity) > 0) {
            commonSender.log(userId, "The user has more than zero lives.o", ClientLogLevels.WARN, true);
        }
        decrementStuff(userStuff, ShopStore.health.getPriceGold(), ObjectEnum.STUFF_GOLD);
        UserHelper.setMaxHealth(userEntity);

        userRepository.save(userEntity);
        userStuffRepository.save(userStuff);

        commonSender.log(userId, "Игрок " + userId + " купил жизней❤❤❤❤❤ " + userStuff, ClientLogLevels.INFO, true);
        commonSender.statistic(userId, ID_BUY_HEALTH);

        sendStuffToUser(userId);
        return mapMapper.entityToDto(userEntity);
    }

    private UserEntity getUser(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Override
    public GotStuffRsDto buyProduct(Long userId, Long index, ObjectEnum objectId) {
        var product = ShopStore.getProduct(index, objectId);
        UserStuffEntity userStuff = getUserStuff(userId);

        decrementStuff(userStuff, product.getPriceGold(), ObjectEnum.STUFF_GOLD);
        incrementStuff(userStuff, product);

        userStuffRepository.save(userStuff);

        commonSender.statistic(userId, getStatIdFromObjectId(product));
        commonSender.log(userId,
                "Игрок " + userId + " купил " + product.getObjectEnum().getComment() +
                        ", теперь: " + userStuff.getQuantity(product.getObjectEnum()) +
                        ", 💰 " + userStuff.getGoldQty(),
                ClientLogLevels.INFO, true);

        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public GotStuffRsDto spendMagic(Long userId, ObjectEnum objectId) {
        var userStuff = getUserStuff(userId);

        decrementStuff(userStuff, 1L, objectId);

        commonSender.statistic(userId, getUsedStatFromObject(objectId));
        commonSender.log(userId, "Игрок " + userId + " использовал " + objectId.getComment() +
                " теперь " + userStuff.toString(), ClientLogLevels.INFO, true);

        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public DoOrderChangeAnswerRsDto doOrderChange(Long tid, Long socNetUserId, Long extOrderId, Long itemPrice, SocNetType socNetType) {

        if (!ShopStore.goldProductByPriceExists(itemPrice)) {
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(tid)
                    .response(vkErrorItemPriceNotFound)
                    .build();
        }

        ProductDto product = ShopStore.getGoldProductByPrice(itemPrice);

        var userOptional = userRepository.findBySocNetTypeIdAndSocNetUserId(socNetType.getId(), socNetUserId);
        if (userOptional.isEmpty()) {
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(tid)
                    .response(vkErrorCommon)
                    .build();
        }
        var user = userOptional.get();

        Optional<PaymentEntity> payment = paymentRepository.findByOrderId(extOrderId);
        if (payment.isPresent()) {
            return DoOrderChangeAnswerRsDto.builder()
                    .tid(tid)
                    .response(vkErrorCommon)
                    .build();
        }


        PaymentEntity entity = paymentMapper.toEntity(System.currentTimeMillis() / 1000L, user.getId(), extOrderId, itemPrice);
        PaymentEntity newOrder = paymentRepository.save(entity);

        stuffService.giveAGold(user.getId(), product.getQuantity());

        sendStuffToUser(user.getId());

        commonSender.statistic(user.getId(), ID_BUY_VK_MONEY, newOrder.getId().toString(), itemPrice.toString());

        return DoOrderChangeAnswerRsDto.builder()
                .tid(tid)
                .response(VKResponseDoOrderSuccessRsDto.builder()
                        .orderId(extOrderId)
                        .appOrderId(newOrder.getId())
                        .build())
                .build();
    }

    private static StatisticEnum getStatIdFromObjectId(ProductDto product) {
        return switch (product.getObjectEnum()) {
            case STUFF_HUMMER -> StatisticEnum.ID_BUY_HUMMER;
            case STUFF_LIGHTNING -> StatisticEnum.ID_BUY_LIGHTNING;
            case STUFF_SHUFFLE -> StatisticEnum.ID_BUY_SHUFFLE;
            default -> throw new RuntimeException("No found stat for objectId");
        };
    }

    private static void incrementStuff(UserStuffEntity stuff, ProductDto product) {
        switch (product.getObjectEnum()) {
            case STUFF_HUMMER -> stuff.setHummerQty(stuff.getHummerQty() + product.getQuantity());
            case STUFF_LIGHTNING -> stuff.setLightningQty(stuff.getHummerQty() + product.getQuantity());
            case STUFF_SHUFFLE -> stuff.setShuffleQty(stuff.getShuffleQty() + product.getQuantity());
            case STUFF_GOLD -> stuff.setGoldQty(stuff.getGoldQty() + product.getQuantity());
        }
    }

    private static void decrementStuff(UserStuffEntity stuff, Long quality, ObjectEnum objectId) {
        switch (objectId) {
            case STUFF_HUMMER -> stuff.setHummerQty(stuff.getHummerQty() - quality);
            case STUFF_LIGHTNING -> stuff.setLightningQty(stuff.getLightningQty() - quality);
            case STUFF_SHUFFLE -> stuff.setShuffleQty(stuff.getShuffleQty() - quality);
            case STUFF_GOLD -> stuff.setGoldQty(stuff.getGoldQty() - quality);
        }
    }

    private static StatisticEnum getUsedStatFromObject(ObjectEnum objectId) {
        switch (objectId) {
            case STUFF_HUMMER -> {
                return ID_HUMMER_USE;
            }
            case STUFF_LIGHTNING -> {
                return ID_LIGHTNING_USE;
            }
            case STUFF_SHUFFLE -> {
                return ID_SHUFFLE_USE;
            }
        }
        throw new RuntimeException("Not found stat for objectIdo");
    }

    private void sendStuffToUser(Long userId) {
        var stuff = getUserStuff(userId);
        GotStuffRsDto stuffRsDto = stuffMapper.entityToDto(stuff);
        kafkaTemplate.send("topic-client", stuffRsDto);
    }

    private UserStuffEntity getUserStuff(Long userId) {
        return userStuffRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User stuff not found"));
    }
}
