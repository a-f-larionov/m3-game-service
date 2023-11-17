package m3.gameplay.dto.rs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class ScoreRsDto {

    private Long userId;
    private Long pointId;
    private Long score;

}
