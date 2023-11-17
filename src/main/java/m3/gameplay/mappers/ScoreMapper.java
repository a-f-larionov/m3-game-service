package m3.gameplay.mappers;

import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.ScoreRsDto;
import m3.lib.entities.UserPointEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScoreMapper {

    GotPointTopScoreRsDto toDto(Long userId, Long place1Uid, Long place2Uid, Long place3Uid, Long pos, Long pointId);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "rows", source = "rows")
    GotScoresRsDto toDto(Long userId, List<UserPointEntity> rows);

    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "pointId", source = "id.pointId")
    ScoreRsDto toDo(UserPointEntity entity);
}
