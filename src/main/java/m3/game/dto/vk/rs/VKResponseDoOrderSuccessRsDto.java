package m3.game.dto.vk.rs;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class VKResponseDoOrderSuccessRsDto implements VKResponseDoOrderRsDto {
    private Long orderId;
    private Long appOrderId;
}
