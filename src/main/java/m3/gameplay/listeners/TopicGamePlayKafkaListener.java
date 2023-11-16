package m3.gameplay.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.gameplay.dto.rq.*;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.services.MapService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "topic-gameplay")
public class TopicGamePlayKafkaListener {

    private final MapService mapService;

    @KafkaHandler
    @SendTo("topic-client")
    public GotMapInfoRsDto sendMeMapInfoRqDto(SendMeMapInfoRqDto rq) {
        return mapService.getMapInfo(rq.getUserId(), rq.getMapId());
    }

    @KafkaHandler
    @SendTo("topic-client")
    public GotScoresRsDto sendMeScoresRqDto(SendMeScoresRqDto rq) {
        return mapService.getScores(rq.getUserId(), rq.getPids(), rq.getUids());
    }

    @KafkaHandler
    @SendTo("topic-client")
    public GotPointTopScoreRsDto sendMePointTopScore(SendMePointTopScoreRqDto rq) {
        return mapService.gotPointTopScore(rq.getUserId(), rq.getPointId(), rq.getScore(), rq.getFids());
    }

    @KafkaHandler
    public void onFinish(OnFinishRqDto rq) {
        mapService.onFinish(rq.getUserId(), rq.getPointId(), rq.getScore(), rq.getChestId());
    }

    @KafkaHandler
    public void sendMeStuff(SendMeStuffRqDto rq) {
        System.out.println(rq);
    }
}