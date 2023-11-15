package m3.map.services;

import m3.map.dto.MapDto;
import m3.map.dto.rs.GotMapInfoRsDto;
import m3.map.dto.rs.GotPointTopScoreRsDto;

import java.util.List;

public interface MapService {
    boolean existsMap(Long mapId);

    MapDto getById(Long mapId);

    GotMapInfoRsDto getMapInfo(Long mapId, Long userId);

    GotMapInfoRsDto getScores(Long userId);

    void onFinish(Long userId, Long pointId, Long score, Long chestId);

    GotPointTopScoreRsDto gotPointTopScore(Long userId, Long pointId, Long score, List<Long> fids, Long chunks);

}
