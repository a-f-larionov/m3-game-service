package m3.map.and.points.dto.rq;

import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rq.UserIdRqDto;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SendMeMapInfoRqDto extends UserIdRqDto {
    private Long mapId ;
}
