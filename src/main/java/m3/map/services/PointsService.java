package m3.map.services;

import m3.map.dto.PointDto;

import java.util.List;

public interface PointsService {

    List<PointDto> getPointsByMapId(Long mapId);
}
