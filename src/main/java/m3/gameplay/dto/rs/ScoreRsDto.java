package m3.gameplay.dto.rs;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ScoreRsDto {

    private Long userId;
    private Long pointId;
    private Long score;
}
