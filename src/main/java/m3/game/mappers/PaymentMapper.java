package m3.game.mappers;

import m3.lib.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "time", source = "time")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "itemPrice", source = "itemPrice")
    PaymentEntity toEntity(Long time, Long userId, Long orderId, Long itemPrice);
}
