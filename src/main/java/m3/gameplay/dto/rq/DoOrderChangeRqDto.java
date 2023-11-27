package m3.gameplay.dto.rq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.enums.SocNetType;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = true)
public class DoOrderChangeRqDto {

    private Long tid;

    @JsonProperty("receiver_id")
    private Long receiverId;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("item_price")
    private Long itemPrice;

    private SocNetType socNetType;
}
