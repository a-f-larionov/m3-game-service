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

        //@todo-a long -> int, because array.length is integer
        Long firstPointId = MapSettings.getFirstPointId(mapId);
        Long lastPointId = MapSettings.getLastPointId(mapId);

        //@todo-a points = getByIdFromTo(fId, lId);
        List<PointDto> points = new ArrayList<>();
        for (Long id = firstPointId; id <= lastPointId; id++) {
            points.add(getById(id));
        }
        return points;
    }

    public PointDto getById(Long id) {
        // @todo-a getById
        return PointStore.points.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point not found"));
    }
}
