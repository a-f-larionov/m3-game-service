package m3.gameplay.dto.rq;

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

    private Long score;
    private Long pointId;
    private List<Long> fids;

}
