package m3.game.dto.rq;

import jakarta.validation.constraints.NotEmpty;
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
public class SendMeScoresRqDto extends UserIdRqDto {
    @NotEmpty
    private List<Long> uids;
    @NotEmpty
    private List<Long> pids;
}
