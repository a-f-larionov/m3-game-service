package m3.gameplay.controllers;

import lombok.RequiredArgsConstructor;
import m3.gameplay.dto.rs.DoOrderChangeAnswerRsDto;
import m3.gameplay.services.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SharedController {

    private final PaymentService paymentService;

    @GetMapping("/service/standalone_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto standaloneBuy(@RequestParam("receiver_id") Long socNetUserId,
                                                  @RequestParam("order_id") Long orderId,
                                                  @RequestParam("item_price") Long itemPrice) {
        return paymentService.standaloneBuy(socNetUserId, orderId, itemPrice);
    }

    @GetMapping("/service/vk_buy")
    @ResponseBody
    public DoOrderChangeAnswerRsDto vkBuy(){

        return paymentService.vkBuy();
    }
}
