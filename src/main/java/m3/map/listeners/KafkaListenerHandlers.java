package m3.map.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.map.dto.rq.OnFinishRqDto;
import m3.map.dto.rq.SendMeMapInfoRqDto;
import m3.map.dto.rq.SendMePointTopScoreRqDto;
import m3.map.dto.rq.SendMeScoresRqDto;
import m3.map.dto.rs.GotMapInfoRsDto;
import m3.map.services.MapService;
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

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeMapInfoRqDto(SendMeMapInfoRqDto rq) {
        return mapService.getMapInfo(rq.getMapId(), rq.getUserId());
    }

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeScoresRqDto(SendMeScoresRqDto rq) {
        return mapService.getScores(rq.getUserId());
    }

    @KafkaHandler
    public void sendMePointTopScoreRqDto(SendMePointTopScoreRqDto rq) {
        System.out.println(rq);
    }

    @KafkaHandler
    public void onFinish(OnFinishRqDto rq) {
        System.out.println(rq);
        mapService.onFinish(rq.getUserId(), rq.getPointId(), rq.getScore(), rq.getChestId());
    }
}