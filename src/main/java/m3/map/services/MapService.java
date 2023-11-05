package m3.map.services;

import m3.map.dto.MapDto;
import m3.map.dto.rs.GotMapInfoRsDto;

public interface MapService {
    boolean existsMap(Long mapId);

    MapDto getById(Long mapId);

    GotMapInfoRsDto getMapInfo(Long mapId, Long userId);

    GotMapInfoRsDto getScores(Long userId);

    void onFinish(Long userId, Long pointId, Long score, Long chestId);
}
