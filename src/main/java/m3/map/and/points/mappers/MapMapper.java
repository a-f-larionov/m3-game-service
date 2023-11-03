package m3.map.and.points.mappers;

import m3.map.and.points.data.MapData;
import m3.map.and.points.data.PointData;
import m3.map.and.points.dto.rs.GotMapInfoRsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapMapper {

    GotMapInfoRsDto entitiestoRsDto(Long userId, Long mapId, MapData map, List<PointData> points);
}
