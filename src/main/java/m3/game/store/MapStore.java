package m3.game.store;

import m3.game.dto.MapDto;

import java.util.List;

public class MapStore {
    public static List<MapDto> maps = List.of(
            new MapDto(1L, "map-001.png"),
            new MapDto(2L, "map-002.png"),
            new MapDto(3L, "map-003.png"),
            new MapDto(4L, "map-001.png"),
            new MapDto(5L, "map-002.png"),
            new MapDto(6L, "map-003.png"),
            new MapDto(7L, "map-001.png"),
            new MapDto(8L, "map-002.png"),
            new MapDto(9L, "map-003.png"),
            new MapDto(10L, "map-001.png")
    );
}
