package m3.map.and.points.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.map.and.points.dto.rq.SendMeMapInfoRqDto;
import m3.map.and.points.dto.rq.SendMeScoresRqDto;
import m3.map.and.points.dto.rs.GotMapInfoRsDto;
import m3.map.and.points.mappers.MapMapper;
import m3.map.and.points.services.MapService;
import m3.map.and.points.services.PointsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "topic-map-and-points", groupId = "2")
public class KafkaListenerHandlers {

    private final MapService mapService;
    private final PointsService pointsService;
    private final MapMapper mapMapper;

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeMapInfoRqDto(SendMeMapInfoRqDto rq) {

        if (!mapService.existsMap(rq.getMapId())) {
            throw new RuntimeException("Map not found");
        } else {
            var map = mapService.getById(rq.getMapId());
            var points = pointsService.getPointsByMapId(rq.getMapId());
            return mapMapper.entitiestoRsDto(
                    rq.getUserId(),
                    rq.getMapId(),
                    map,
                    points
            );
        }

//        if (!DataMap.existsMap(mapId)) return Logs.log("no map found:" + mapId, Logs.LEVEL_WARNING, cntx);
//
//        map = DataMap.getMap(mapId);
//        points = DataPoints.getPointsByMapId(mapId);
//
//        CAPIMap.gotMapsInfo(cntx.userId, mapId, map, points);
//    }
//
//    //@todo-method
//        Kafka.sendToMapAndPoints({mapId: mapId}, cntx.user.id, "SendMeMapInfoRqDto");
    }

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeScoresRqDto(SendMeScoresRqDto rq) {
        return new GotMapInfoRsDto();
    }
}