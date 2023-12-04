package m3.game.dto.vk.rs;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VKResponseDoOrderErrorRsDto implements VKResponseDoOrderRsDto{
    private Long errorCode;
    private String errorMsg;
    private Boolean crtitcal;
}
