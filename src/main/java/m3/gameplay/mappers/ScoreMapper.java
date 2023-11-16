package m3.gameplay.mappers;

import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.lib.entities.UserPointEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    GotPointTopScoreRsDto toDto(Long userId, Long place1Uid, Long place2Uid, Long place3Uid, Long pos, Long pointId);

    GotScoresRsDto toDto(Long userId, List<UserPointEntity> rows);
}
