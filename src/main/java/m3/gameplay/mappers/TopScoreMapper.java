package m3.gameplay.mappers;

import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopScoreMapper {

    GotPointTopScoreRsDto toDto(Long userId, Long place1Uid, Long place2Uid, Long place3Uid, Long pos, Long pointId);
}
