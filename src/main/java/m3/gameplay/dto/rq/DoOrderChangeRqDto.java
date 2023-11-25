package m3.gameplay.dto.rq;

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

    private Long receiverId;
    private Long orderId;
    private Long itemPrice;
    private Long tid;
    private SocNetType socNetType;
    private String buyPrefix;
}
