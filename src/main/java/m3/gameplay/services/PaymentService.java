package m3.gameplay.services;

import m3.gameplay.dto.rs.DoOrderChangeAnswerRsDto;
import m3.lib.enums.SocNetType;

import java.util.Map;

public interface PaymentService {

    DoOrderChangeAnswerRsDto doOrderChange(Long tid, Long socNetUserId, Long extOrderId, Long itemPrice, SocNetType socNetType);

    DoOrderChangeAnswerRsDto standaloneBuy(Long socNetUserId, Long orderId, Long itemPrice);

    DoOrderChangeAnswerRsDto vkBuy(Long appId, Long socNetUserId, String sig, Long orderId, Long itemPrice, String notificationType, String status, Map<String,String> params);
}
