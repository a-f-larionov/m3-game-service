package m3.game.services.impl;

import lombok.RequiredArgsConstructor;
import m3.game.dto.PointDto;
import m3.game.services.PointsService;
import m3.game.store.PointStore;
import m3.lib.settings.MapSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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
        return PointStore.points.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point not found"));
    }
}
