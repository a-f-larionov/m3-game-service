package m3.game.services;

import m3.game.dto.PointDto;

import java.util.List;

public interface PointsService {

    List<PointDto> getPointsByMapId(Long mapId);
}
