package m3.map.and.points.services;

import m3.map.and.points.dto.PointDto;

import java.util.List;

public interface PointsService {

    List<PointDto> getPointsByMapId(Long mapId);
}
