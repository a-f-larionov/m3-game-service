package m3.game.dto.rs;


import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.game.dto.vk.rs.VKResponseDoOrderRsDto;

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
