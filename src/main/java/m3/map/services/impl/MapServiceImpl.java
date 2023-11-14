package m3.map.services.impl;

import lombok.RequiredArgsConstructor;
import m3.lib.entities.UserEntity;
import m3.lib.enums.StatisticEnum;
import m3.lib.repositories.UsersPointRepository;
import m3.lib.repositories.UsersRepository;
import m3.map.data_store.MapDataStore;
import m3.map.dto.MapDto;
import m3.map.dto.rs.GotMapInfoRsDto;
import m3.map.kafka.sender.CommonSender;
import m3.map.mappers.MapMapper;
import m3.map.services.ChestsService;
import m3.map.services.MapService;
import m3.map.services.PointsService;
import m3.map.services.StuffService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final PointsService pointsService;
    private final ChestsService chestsService;
    private final StuffService stuffService;
    private final MapMapper mapMapper;
    private final UsersRepository usersRepository;
    private final UsersPointRepository pointRepository;
    private final CommonSender commonSender;

    @Override
    public boolean existsMap(Long mapId) {
        return MapDataStore.maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapDto getById(Long mapId) {
        return MapDataStore.maps.stream()
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
        var user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("Not found"));

        updateUserPoints(userId, pointId, score);
        updateNextPoint(user, pointId, score);
        openChest(userId, chestId);
    }

    private void updateUserPoints(Long userId, Long pointId, Long score) {
        pointRepository.updateUsersPoints(userId, pointId, score);
        commonSender.statistic(userId, StatisticEnum.ID_FINISH_PLAY, pointId.toString(), score.toString());
        //     TopScoreCache.flush(cntx.user.id, pointId);
    }

    private void updateNextPoint(UserEntity user, Long pointId, Long score) {
        if (user.getNextPointId() < pointId + 1) {
            usersRepository.updateNextPoint(user.getId(), pointId + 1);
            commonSender.statistic(user.getId(), StatisticEnum.ID_LEVEL_UP, String.valueOf(pointId + 1), score.toString());
        }
    }


    private void openChest(Long userId, Long chestId) {

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
        commonSender.statistic(userId, StatisticEnum.ID_OPEN_CHEST, chestId.toString());
    }
}
