package m3.gameplay.mappers;

import m3.gameplay.dto.MapDto;
import m3.gameplay.dto.PointDto;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapMapper {

    @Mapping(target = "userId", source = "id")
    UpdateUserInfoRsDto entityToDto(UserEntity user);

    GotMapInfoRsDto entitiestoRsDto(Long userId, Long mapId, MapDto map, List<PointDto> points);
}
