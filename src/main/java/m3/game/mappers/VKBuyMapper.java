package m3.game.mappers;

import m3.game.dto.vk.rq.VKBuyRqBodyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface VKBuyMapper {

    @Mapping(target = "appId", source = "app_id")
    @Mapping(target = "socNetUserId", source = "receiver_id")
    @Mapping(target = "sig", source = "sig")
    @Mapping(target = "orderId", source = "order_id")
    @Mapping(target = "itemPrice", source = "item_price")
    @Mapping(target = "notificationType", source = "notification_type")
    @Mapping(target = "status", source = "status")
    VKBuyRqBodyDto bodyToDto(Map<String, String> map);
}
