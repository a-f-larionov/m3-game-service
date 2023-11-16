package m3.gameplay.mappers;

import m3.gameplay.dto.MapDto;
import m3.gameplay.dto.PointDto;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapMapper {

    GotMapInfoRsDto entitiestoRsDto(Long userId, Long mapId, MapDto map, List<PointDto> points);
}
