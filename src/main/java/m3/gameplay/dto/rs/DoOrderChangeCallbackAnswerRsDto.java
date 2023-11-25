package m3.gameplay.dto.rs;


import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DoOrderChangeCallbackAnswerRsDto {
    private Long tid;
    private String body;
}
