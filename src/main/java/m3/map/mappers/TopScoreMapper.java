package m3.map.mappers;

import m3.map.dto.rs.GotPointTopScoreRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopScoreMapper {

    GotPointTopScoreRsDto toDto(Long place1Uid, Long place2Uid, Long place3Uid, Long pos);
}
