package m3.map.and.points.dto.rs;

import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rs.UserIdRsDto;
import m3.map.and.points.dto.MapDto;
import m3.map.and.points.dto.PointDto;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class GotMapInfoRsDto extends UserIdRsDto {

    private Long mapId;
    private MapDto map;
    private List<PointDto> points;
}
