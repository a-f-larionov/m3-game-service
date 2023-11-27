package m3.gameplay.dto.vk.rs;

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
