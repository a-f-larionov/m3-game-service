package m3.gameplay.services;

import m3.gameplay.dto.PointDto;

import java.util.List;

public interface PointsService {

    List<PointDto> getPointsByMapId(Long mapId);
}
