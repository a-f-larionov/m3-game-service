package m3.gameplay.mappers;

import m3.lib.entities.UserStuffEntity;
import m3.gameplay.dto.rs.GotStuffRsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StuffMapper {

    @Mapping(target = "userId", source = "id")
    GotStuffRsDto entityToDto(UserStuffEntity entity);
}
