package m3.gameplay.services;

import m3.gameplay.dto.MapDto;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.GotStuffRsDto;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.enums.ObjectEnum;

import java.util.List;

public interface MapService {
    boolean existsMap(Long mapId);

    MapDto getById(Long mapId);

    GotMapInfoRsDto getMapInfo(Long mapId, Long userId);

    GotScoresRsDto getScores(Long userId, List<Long> pids, List<Long> uids);

    void onFinish(Long userId, Long pointId, Long score, Long chestId);

    GotPointTopScoreRsDto getPointTopScore(Long userId, Long pointId, Long score, List<Long> fids);

    GotStuffRsDto getUserStuffRsDto(Long userId);

    GotStuffRsDto spendCoinsForTurns(Long userId);

    UpdateUserInfoRsDto buyHealth(Long userId, Long index);

    GotStuffRsDto buyProduct(Long userId, Long index, ObjectEnum objectId);

    GotStuffRsDto spendMagic(Long userId, ObjectEnum objectId);

    void exitGame(Long userId, Long pointId);

    void looseGame(Long userId, Long pointId);
}
