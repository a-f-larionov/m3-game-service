package m3.game.services;

import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.lib.enums.SocNetType;

import java.util.Map;

public interface PaymentService {

    DoOrderChangeAnswerRsDto standaloneBuy(Long socNetUserId, Long itemPrice, Long orderId);

    DoOrderChangeAnswerRsDto vkBuy(Long appId, Long socNetUserId, String sig,
                                   Long itemPrice, Long orderId,
                                   String notificationType, String status,
                                   Map<String, String> params);
    DoOrderChangeAnswerRsDto doOrderChange(Long tid, SocNetType socNetType, Long socNetUserId, Long itemPrice, Long extOrderId);

    boolean checkVKSign(String sig, Map<String, String> params);

    String calcVKSign(Map<String, String> params);
}
