package m3.game.dto.rq;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @NonNull
    private Long tid;
    @JsonProperty("receiver_id")
    @NotNull
    private Long receiverId;
    @JsonProperty("order_id")
    @NotNull
    private Long orderId;
    @JsonProperty("item_price")
    @NotNull
    private Long itemPrice;
    @NotNull
    private SocNetType socNetType;
}
