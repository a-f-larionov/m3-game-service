package m3.map.mappers;

import m3.lib.entities.UserStuffEntity;
import m3.map.dto.rs.GotStuffRsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StuffMapper {

    @Mapping(target = "userId", source = "id")
    GotStuffRsDto entityToDto(UserStuffEntity entity);
}
