package m3.map.and.points.services.impl;

import lombok.RequiredArgsConstructor;
import m3.map.and.points.data.MapData;
import m3.map.and.points.services.MapService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private static List<MapData> maps = List.of(
            new MapData(1L, "map-001.png"),
            new MapData(2L, "map-002.png"),
            new MapData(3L, "map-003.png"),
            new MapData(4L, "map-001.png"),
            new MapData(5L, "map-002.png"),
            new MapData(6L, "map-003.png"),
            new MapData(7L, "map-001.png"),
            new MapData(8L, "map-002.png"),
            new MapData(9L, "map-003.png"),
            new MapData(10L, "map-001.png")
    );

    @Override
    public boolean existsMap(Long mapId) {
        return maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapData getById(Long mapId) {
        return maps.stream()
                .filter(m->m.getId().equals(mapId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Map not found"));
    }
}
