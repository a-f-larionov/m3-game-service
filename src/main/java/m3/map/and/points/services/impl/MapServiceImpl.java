package m3.map.and.points.services.impl;

import lombok.RequiredArgsConstructor;
import m3.map.and.points.data_store.MapDataStore;
import m3.map.and.points.dto.MapDto;
import m3.map.and.points.services.MapService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    @Override
    public boolean existsMap(Long mapId) {
        return MapDataStore.maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapDto getById(Long mapId) {
        return MapDataStore.maps.stream()
                .filter(m->m.getId().equals(mapId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Map not found"));
    }
}
