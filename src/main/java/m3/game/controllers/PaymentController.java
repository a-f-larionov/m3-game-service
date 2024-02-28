package m3.game.controllers;

import lombok.RequiredArgsConstructor;
import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.game.dto.vk.rq.VKBuyRqBodyDto;
import m3.game.mappers.VKBuyMapper;
import m3.game.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final VKBuyMapper vkBuyMapper;

    @GetMapping("/service/standalone_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto standaloneBuyGet(@RequestParam("receiver_id") Long socNetUserId,
                                                     @RequestParam("order_id") Long orderId,
                                                     @RequestParam("item_price") Long itemPrice) {
        return paymentService.standaloneBuy(socNetUserId, itemPrice, orderId);
    }

    @PostMapping(value = "/service/vk_buy",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE
            }
    )
    public DoOrderChangeAnswerRsDto vkBuyPost(@RequestParam Map<String, String> body) {

        VKBuyRqBodyDto dto = vkBuyMapper.toDto(body);

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
