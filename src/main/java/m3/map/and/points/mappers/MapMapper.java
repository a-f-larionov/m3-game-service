package m3.map.and.points.mappers;

import m3.map.and.points.dto.MapDto;
import m3.map.and.points.dto.PointDto;
import m3.map.and.points.dto.rs.GotMapInfoRsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapMapper {

    GotMapInfoRsDto entitiestoRsDto(Long userId, Long mapId, MapDto map, List<PointDto> points);
}
