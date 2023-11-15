package m3.map.services.impl;

import lombok.RequiredArgsConstructor;
import m3.lib.entities.UserPointEntity;
import m3.lib.enums.StatisticEnum;
import m3.lib.repositories.UserPointRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
import m3.map.dto.MapDto;
import m3.map.dto.rs.GotMapInfoRsDto;
import m3.map.dto.rs.GotPointTopScoreRsDto;
import m3.map.dto.rs.GotStuffRsDto;
import m3.map.kafka.sender.CommonSender;
import m3.map.mappers.MapMapper;
import m3.map.mappers.StuffMapper;
import m3.map.mappers.TopScoreMapper;
import m3.map.services.ChestsService;
import m3.map.services.MapService;
import m3.map.services.PointsService;
import m3.map.services.StuffService;
import m3.map.store.MapStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final PointsService pointsService;
    private final ChestsService chestsService;
    private final StuffService stuffService;
    private final MapMapper mapMapper;
    private final StuffMapper stuffMapper;
    private final TopScoreMapper topScoreMapper;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;
    private final UserStuffRepository userStuffRepository;
    private final CommonSender commonSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public boolean existsMap(Long mapId) {
        return MapStore.maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapDto getById(Long mapId) {
        return MapStore.maps.stream()
                .filter(m -> m.getId().equals(mapId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Map not found"));
    }

    @Override
    public GotMapInfoRsDto getMapInfo(Long mapId, Long userId) {
        var map = getById(mapId);
        var points = pointsService.getPointsByMapId(mapId);
        return mapMapper.entitiestoRsDto(
                userId,
                mapId,
                map,
                points
        );
    }

    @Override
    public GotMapInfoRsDto getScores(Long userId) {
        GotMapInfoRsDto gotMapInfoRsDto = new GotMapInfoRsDto();
        gotMapInfoRsDto.setUserId(userId);
        return gotMapInfoRsDto;
    }

    @Override
    public void onFinish(Long userId, Long pointId, Long score, Long chestId) {
        // @todo transactional one for all method
        System.out.println("onFinish");
        // check exists
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        updateUserPoint(userId, pointId, score);
        nextPointUp(userId, pointId);
        giveChest(userId, chestId);
    }

    @Override
    public GotPointTopScoreRsDto gotPointTopScore(Long userId, Long pointId, Long score, List<Long> fids, Long chunks) {

        List<UserPointEntity> topScore = userPointRepository.getTopScore(pointId, fids);
        Long userPosition = userPointRepository.getTopScoreUserPosition(score, pointId, fids, userId);

        return topScoreMapper.toDto(
                topScore.get(0).getId().getUserId(),
                topScore.get(1).getId().getUserId(),
                topScore.get(2).getId().getUserId(),
                userPosition);
    }

    private void updateUserPoint(Long userId, Long pointId, Long score) {
        userPointRepository.updateUserPoint(userId, pointId, score);
        commonSender.statistic(userId, StatisticEnum.ID_FINISH_PLAY, pointId.toString(), score.toString());
        //     TopScoreCache.flush(cntx.user.id, pointId);
    }

    private void nextPointUp(Long userId, Long pointId) {
        userRepository.nextPointUp(userId, pointId + 1);
        commonSender.statistic(userId, StatisticEnum.ID_LEVEL_UP, String.valueOf(pointId + 1));
    }

    private void giveChest(Long userId, Long chestId) {
        // zere means - no chest to open
        if (chestId.equals(0L)) return;

        var chest = chestsService.getById(chestId);

        chest.getPrizes().forEach(prize -> {
            switch (prize.getId()) {
                case STUFF_HUMMER -> stuffService.giveAHummer(userId, prize.getCount());
                case STUFF_LIGHTNING -> stuffService.giveALightning(userId, prize.getCount());
                case STUFF_SHUFFLE -> stuffService.giveAShuffle(userId, prize.getCount());
                case STUFF_GOLD -> stuffService.giveAGold(userId, prize.getCount());
            }
        });

        var stuff = userStuffRepository.getByUserId(userId);
        GotStuffRsDto dto = stuffMapper.entityToDto(stuff);
        kafkaTemplate.send("topic-client", dto);

        //@Todo transaction control!
        commonSender.statistic(userId, StatisticEnum.ID_OPEN_CHEST, chestId.toString());
    }
}
