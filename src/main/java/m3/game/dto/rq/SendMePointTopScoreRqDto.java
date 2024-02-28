package m3.game.dto.rq;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rq.UserIdRqDto;

import java.util.List;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SendMePointTopScoreRqDto extends UserIdRqDto {
    @NotNull
    private Long score;
    @NotNull
    private Long pointId;
    @NotEmpty
    private List<Long> fids;
}
