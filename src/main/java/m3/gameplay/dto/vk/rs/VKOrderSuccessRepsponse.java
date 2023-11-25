package m3.gameplay.dto.vk.rs;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class VKOrderSuccessRepsponse {
    private Long orderId;
    private Long appOrderId;
}
