package m3.map.and.points.services.impl;

import lombok.RequiredArgsConstructor;
import m3.lib.settings.MapSettings;
import m3.map.and.points.data_store.PointDataStore;
import m3.map.and.points.dto.PointDto;
import m3.map.and.points.services.PointsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

        @Override
    public List<PointDto> getPointsByMapId(Long mapId) {

        Long firstPointId = MapSettings.getFirstPointId(mapId);
        Long lastPointId = MapSettings.getLastPointId(mapId);

        List<PointDto> points = new ArrayList<>();
        for (Long id = firstPointId; id <= lastPointId; id++) {
            points.add(getById(id));
        }
        return points;
    }

    public PointDto getById(Long id) {
        return PointDataStore.points.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point not found"));
    }
}
