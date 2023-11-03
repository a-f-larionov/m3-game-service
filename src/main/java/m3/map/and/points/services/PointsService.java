package m3.map.and.points.services;

import m3.map.and.points.data.PointData;

import java.util.List;

public interface PointsService {

    List<PointData> getPointsByMapId(Long mapId);
}
