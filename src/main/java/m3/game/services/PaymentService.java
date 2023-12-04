package m3.game.services;

import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.lib.enums.SocNetType;

public interface PaymentService {

    DoOrderChangeAnswerRsDto standaloneBuy(Long socNetUserId, Long itemPrice, Long orderId);

    DoOrderChangeAnswerRsDto vkBuy(Long appId, Long socNetUserId, String sig,
                                   Long itemPrice, Long orderId,
                                   String notificationType, String status);

    DoOrderChangeAnswerRsDto doOrderChange(Long tid, SocNetType socNetType, Long socNetUserId, Long itemPrice, Long extOrderId);
}
