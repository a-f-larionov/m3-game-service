package m3.game.controllers;

import jakarta.validation.constraints.NotNull;
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

    // @todo-a devide controllers
    // @todo-a /service -> /payment

    @GetMapping("/service/profiler")
    public String methodProfiles() {
        StringBuilder out = new StringBuilder();
        //@todo-a Profiler as service in autoconfiguration
        //@todo-a profiler.getTextReport();
        for (Map.Entry<String, AtomicInteger> entry : ProfilerAop.data.entrySet()) {
            String a = entry.getKey();
            AtomicInteger b = entry.getValue();
            out.append(a).append(" - ").append(b).append("<br>\r\n");
        }
        return out.toString();
    }

    @GetMapping("/payments/standalone_buy")
    public DoOrderChangeAnswerRsDto standaloneBuyGet(
            @RequestParam("receiver_id") @NotNull Long socNetUserId,
            @RequestParam("order_id") @NotNull Long orderId,
            @RequestParam("item_price") @NotNull Long itemPrice) {
        return paymentService.standaloneBuy(socNetUserId, itemPrice, orderId);
    }

    //@todo-a test removed curly braces
    @PostMapping(value = "/payments/vk_buy",
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
