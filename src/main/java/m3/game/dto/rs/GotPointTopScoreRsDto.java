package m3.game.dto.rs;


import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rs.UserIdRsDto;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GotPointTopScoreRsDto extends UserIdRsDto {

    private Long pointId;
    private Long place1Uid;
    private Long place2Uid;
    private Long place3Uid;
    private Long pos;
}
