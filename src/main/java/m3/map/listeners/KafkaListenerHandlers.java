package m3.map.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.map.dto.rq.OnFinishRqDto;
import m3.map.dto.rq.SendMeMapInfoRqDto;
import m3.map.dto.rq.SendMeScoresRqDto;
import m3.map.dto.rs.GotMapInfoRsDto;
import m3.map.mappers.MapMapper;
import m3.map.services.MapService;
import m3.map.services.PointsService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "topic-map")
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
    }

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeScoresRqDto(SendMeScoresRqDto rq) {
        return new GotMapInfoRsDto();
    }

    @KafkaHandler
    public void onFinish(OnFinishRqDto rq) {

    }
}