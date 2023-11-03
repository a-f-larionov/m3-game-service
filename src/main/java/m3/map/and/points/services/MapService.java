package m3.map.and.points.services;

import m3.map.and.points.dto.MapDto;

public interface MapService {
    boolean existsMap(Long mapId);

    MapDto getById(Long mapId);
}
