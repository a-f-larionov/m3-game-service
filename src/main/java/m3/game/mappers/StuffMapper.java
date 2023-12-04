package m3.game.mappers;

import m3.game.dto.rs.GotStuffRsDto;
import m3.lib.entities.UserStuffEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StuffMapper {

    @Mapping(target = "userId", source = "id")
    GotStuffRsDto entityToDto(UserStuffEntity entity);
}
