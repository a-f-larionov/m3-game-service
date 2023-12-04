package m3.game.controllers;

import lombok.RequiredArgsConstructor;
import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.game.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SharedController {

    private final PaymentService paymentService;

    @GetMapping("/service/standalone_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto standaloneBuyGet(@RequestParam("receiver_id") Long socNetUserId,
                                                  @RequestParam("order_id") Long orderId,
                                                  @RequestParam("item_price") Long itemPrice) {
        return paymentService.standaloneBuy(socNetUserId, itemPrice, orderId);
    }

    @GetMapping("/service/vk_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto vkBuyGet(
            @RequestParam("app_id") Long appId,
            @RequestParam("receiver_id") Long socNetUserId,
            @RequestParam("sig") String sig,
            @RequestParam("order_id") Long orderId,
            @RequestParam("item_price") Long itemPrice,
            @RequestParam("notification_type") String notificationType,
            @RequestParam("status") String status
    ) {
        return paymentService.vkBuy(appId, socNetUserId, sig, itemPrice, orderId, notificationType, status);
    }

    @PostMapping("/service/vk_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto vkBuyPost(
            @RequestParam("app_id") Long appId,
            @RequestParam("receiver_id") Long socNetUserId,
            @RequestParam("sig") String sig,
            @RequestParam("order_id") Long orderId,
            @RequestParam("item_price") Long itemPrice,
            @RequestParam("notification_type") String notificationType,
            @RequestParam("status") String status
    ) {
        return paymentService.vkBuy(appId, socNetUserId, sig, itemPrice, orderId, notificationType, status);
    }
}
