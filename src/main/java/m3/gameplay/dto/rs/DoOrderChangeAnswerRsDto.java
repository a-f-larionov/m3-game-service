package m3.gameplay.dto.rs;


import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.gameplay.dto.vk.rs.VKResponseDoOrderRsDto;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DoOrderChangeAnswerRsDto {
    private Long tid;
    private VKResponseDoOrderRsDto response;
}
