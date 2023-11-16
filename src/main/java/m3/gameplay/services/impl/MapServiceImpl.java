package m3.gameplay.services.impl;

import lombok.RequiredArgsConstructor;
import m3.gameplay.dto.MapDto;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.GotStuffRsDto;
import m3.gameplay.kafka.sender.CommonSender;
import m3.gameplay.mappers.MapMapper;
import m3.gameplay.mappers.ScoreMapper;
import m3.gameplay.mappers.StuffMapper;
import m3.gameplay.services.ChestsService;
import m3.gameplay.services.MapService;
import m3.gameplay.services.PointsService;
import m3.gameplay.services.StuffService;
import m3.gameplay.store.MapStore;
import m3.lib.entities.UserPointEntity;
import m3.lib.enums.StatisticEnum;
import m3.lib.repositories.UserPointRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
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
    private final ScoreMapper scoreMapper;
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
    public GotMapInfoRsDto getMapInfo(Long userId, Long mapId) {
        // @todo user set to first positions
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
    public GotScoresRsDto getScores(Long userId, List<Long> pids, List<Long> uids) {
        List<UserPointEntity> scores = userPointRepository.getScores(pids, uids);
        return scoreMapper.toDto(userId, scores );
    }

    @Override
    public void onFinish(Long userId, Long pointId, Long score, Long chestId) {
        // @todo transactional one for all method
        // check exists
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        updateUserPoint(userId, pointId, score);
        nextPointUp(userId, pointId);
        giveChest(userId, chestId);
    }

    @Override
    public GotPointTopScoreRsDto gotPointTopScore(Long userId, Long pointId, Long score, List<Long> fids) {

        List<UserPointEntity> topScore = userPointRepository.getTopScore(pointId, fids);
        Long userPosition = userPointRepository.getTopScoreUserPosition(score, pointId, fids, userId);

        return scoreMapper.toDto(
                userId,
                topScore.get(0).getId().getUserId(),
                topScore.get(1).getId().getUserId(),
                topScore.get(2).getId().getUserId(),
                userPosition,
                pointId
        );
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

    public void giveChest(Long userId, Long chestId) {
        // zero means - no chest to open
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
