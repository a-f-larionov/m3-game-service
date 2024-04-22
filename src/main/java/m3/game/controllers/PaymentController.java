package m3.game.controllers;

import lombok.RequiredArgsConstructor;
import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.game.dto.vk.rq.VKBuyRqBodyDto;
import m3.game.mappers.VKBuyMapper;
import m3.game.services.PaymentService;
import m3.lib.ProfileMethods;
import m3.lib.store.ProfilerAop;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequiredArgsConstructor
@ProfileMethods
public class PaymentController {

    private final PaymentService paymentService;
    private final VKBuyMapper vkBuyMapper;

    // @todo devide controllers
    // @todo /service -> /payment

    @GetMapping("/service/web/profiler")
    public String methodProfiles() {
        StringBuilder out = new StringBuilder();
        //@todo Profiler as service in autoconfiguration
        //@todo profiler.getTextReport();
        for (Map.Entry<String, AtomicInteger> entry : ProfilerAop.data.entrySet()) {
            String a = entry.getKey();
            AtomicInteger b = entry.getValue();
            out.append(a).append(" - ").append(b).append("<br>\r\n");
        }
        return out.toString();
    }

    @GetMapping("/service/web/standalone_buy")
    public DoOrderChangeAnswerRsDto standaloneBuyGet(
            @RequestParam("receiver_id") Long socNetUserId,
            @RequestParam("order_id") Long orderId,
            @RequestParam("item_price") Long itemPrice) {
        return paymentService.standaloneBuy(socNetUserId, itemPrice, orderId);
    }

    // @todo produces default?
    //@todo test removed curly braces
    @PostMapping(value = "/service/web/vk_buy",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DoOrderChangeAnswerRsDto vkBuyPost(@RequestParam Map<String, String> body) {

        VKBuyRqBodyDto dto = vkBuyMapper.bodyToDto(body);

        return paymentService.vkBuy(
                dto.getAppId(),
                dto.getSocNetUserId(),
                dto.getSig(),
                dto.getItemPrice(),
                dto.getOrderId(),
                dto.getNotificationType(),
                dto.getStatus(),
                body
        );
    }
}
