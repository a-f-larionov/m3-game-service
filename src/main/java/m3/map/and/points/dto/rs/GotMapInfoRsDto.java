package m3.map.and.points.dto.rs;

import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rs.UserIdRsDto;
import m3.map.and.points.data.MapData;
import m3.map.and.points.data.PointData;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GotMapInfoRsDto extends UserIdRsDto {

    private Long mapId;
    private MapData map;
    private List<PointData> points;

}
