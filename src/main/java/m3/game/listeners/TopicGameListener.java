package m3.game.listeners;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.game.dto.rq.*;
import m3.game.dto.rs.*;
import m3.game.services.MapService;
import m3.game.services.PaymentService;
import m3.lib.ProfileMethods;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.enums.ObjectEnum;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "#{topicNames.currentTopic}")
@ProfileMethods
@SendTo("#{topicNames.CLIENT}")
public class TopicGameListener {

    private final MapService mapService;
    private final PaymentService paymentService;

    @KafkaHandler
    public GotMapInfoRsDto sendMeMapInfoRqDto(@Valid SendMeMapInfoRqDto rq) {
        return mapService.getMapInfo(rq.getUserId(), rq.getMapId());
    }

    @KafkaHandler
    public GotScoresRsDto sendMeScoresRqDto(@Valid SendMeScoresRqDto rq) {
        return mapService.getScores(rq.getUserId(), rq.getPids(), rq.getUids());
    }

    @KafkaHandler
    public GotPointTopScoreRsDto sendMePointTopScore(@Valid SendMePointTopScoreRqDto rq) {
        return mapService.getPointTopScore(rq.getUserId(), rq.getPointId(), rq.getScore(), rq.getFids());
    }

    @KafkaHandler
    public void onFinish(@Valid OnFinishRqDto rq) {
        mapService.onFinish(rq.getUserId(), rq.getPointId(), rq.getScore(), rq.getChestId());
    }

    @KafkaHandler
    public GotStuffRsDto sendMeStuff(@Valid SendMeStuffRqDto rq) {
        return mapService.getUserStuffRsDto(rq.getUserId());
    }

    @KafkaHandler
    public GotStuffRsDto spendCoinsForTurnsRqDto(@Valid SpendCoinsForTurnsRqDto rq) {
        return mapService.spendCoinsForTurns(rq.getUserId());
    }

    @KafkaHandler
    public UpdateUserInfoRsDto buyHealth(@Valid BuyHealthRqDto rq) {
        return mapService.buyHealth(rq.getUserId(), rq.getIndex());
    }

    //@todo-a Buy [ Hummer, Lightning, Shuffle ] RqDto.
    //@todo-a Used [ Hummer, Lightning, Shuffle ] RqDto.
    @KafkaHandler
    public GotStuffRsDto buyHummer(@Valid BuyHummerRqDto rq) {
        return mapService.buyProduct(rq.getUserId(), rq.getIndex(), ObjectEnum.STUFF_HUMMER);
    }

    @KafkaHandler
    public GotStuffRsDto buyLightning(@Valid BuyLightningRqDto rq) {
        return mapService.buyProduct(rq.getUserId(), rq.getIndex(), ObjectEnum.STUFF_LIGHTNING);
    }

    @KafkaHandler
    public GotStuffRsDto buyShuffle(@Valid BuyShuffleRqDto rq) {
        return mapService.buyProduct(rq.getUserId(), rq.getIndex(), ObjectEnum.STUFF_SHUFFLE);
    }

    @KafkaHandler
    public GotStuffRsDto usedHummer(@Valid UsedHummerRqDto rq) {
        return mapService.spendMagic(rq.getUserId(), ObjectEnum.STUFF_HUMMER);
    }

    @KafkaHandler
    public GotStuffRsDto usedLightning(@Valid UsedLightningRqDto rq) {
        return mapService.spendMagic(rq.getUserId(), ObjectEnum.STUFF_LIGHTNING);
    }

    @KafkaHandler
    public GotStuffRsDto usedShuffle(@Valid UsedShuffleRqDto rq) {
        return mapService.spendMagic(rq.getUserId(), ObjectEnum.STUFF_SHUFFLE);
    }

    @KafkaHandler
    public void exitGame(@Valid ExitGameRqDto rq) {
        mapService.exitGame(rq.getUserId(), rq.getPointId());
    }

    @KafkaHandler
    public void looseGame(@Valid LooseGameRqDto rq) {
        mapService.looseGame(rq.getUserId(), rq.getPointId());
    }
}