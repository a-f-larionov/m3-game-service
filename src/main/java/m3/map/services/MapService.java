package m3.map.services;

import m3.map.dto.MapDto;

public interface MapService {
    boolean existsMap(Long mapId);

    MapDto getById(Long mapId);
}
