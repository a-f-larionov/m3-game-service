package m3.map.and.points.services;

import m3.map.and.points.data.MapData;

public interface MapService {
    boolean existsMap(Long mapId);

    MapData getById(Long mapId);
}
