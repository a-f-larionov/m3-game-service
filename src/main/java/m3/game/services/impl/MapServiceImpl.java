package m3.game.services.impl;

import lombok.RequiredArgsConstructor;
import m3.game.dto.MapDto;
import m3.game.dto.rs.GotMapInfoRsDto;
import m3.game.dto.rs.GotPointTopScoreRsDto;
import m3.game.dto.rs.GotScoresRsDto;
import m3.game.dto.rs.GotStuffRsDto;
import m3.game.mappers.MapMapper;
import m3.game.mappers.PaymentMapper;
import m3.game.mappers.ScoreMapper;
import m3.game.mappers.StuffMapper;
import m3.game.services.ChestsService;
import m3.game.services.MapService;
import m3.game.services.PointsService;
import m3.game.services.StuffService;
import m3.game.store.MapStore;
import m3.lib.dto.ProductDto;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.entities.UserEntity;
import m3.lib.entities.UserPointEntity;
import m3.lib.entities.UserStuffEntity;
import m3.lib.enums.ClientLogLevels;
import m3.lib.enums.ObjectEnum;
import m3.lib.enums.StatisticEnum;
import m3.lib.helpers.UserHelper;
import m3.lib.kafka.sender.CommonSender;
import m3.lib.repositories.PaymentRepository;
import m3.lib.repositories.UserPointRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
import m3.lib.store.ShopStore;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static m3.lib.enums.StatisticEnum.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MapServiceImpl implements MapService {

    private final PointsService pointsService;
    private final ChestsService chestsService;
    private final StuffService stuffService;
    private final MapMapper mapMapper;
    private final StuffMapper stuffMapper;
    private final ScoreMapper scoreMapper;
    //@todo-a code analyze
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;
    private final UserStuffRepository userStuffRepository;
    private final PaymentRepository paymentRepository;
    private final CommonSender commonSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public boolean existsMap(Long mapId) {
        //@todo-a ?
        return MapStore.maps.stream()
                .anyMatch(m -> m.getId().equals(mapId));
    }

    @Override
    public MapDto getById(Long mapId) {
        //@todo-a getById
        return MapStore.maps.stream()
                .filter(m -> m.getId().equals(mapId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Map not found"));
    }

    @Override
    public GotMapInfoRsDto getMapInfo(Long userId, Long mapId) {
        // @todo-a user set to first positions
        var map = getById(mapId);
        var points = pointsService.getPointsByMapId(mapId);
        return mapMapper.entitiesToRsDto(
                userId,
                mapId,
                map,
                points
        );
    }

    @Override
    public GotScoresRsDto getScores(Long userId, List<Long> pids, List<Long> uids) {
        List<UserPointEntity> scores = userPointRepository.getScores(pids, uids);
        return scoreMapper.toDto(userId, scores);
    }

    @Override
    public void onFinish(Long userId, Long pointId, Long score, Long chestId) {
        // @todo-a transactional one for all methods set @Transactional to alll listeners and controllers
        isUserExistsOrThrowException(userId);

        updateUserPoint(userId, pointId, score);
        nextPointUp(userId, pointId);
        giveChest(userId, chestId);
    }

    @Override
    public GotPointTopScoreRsDto getPointTopScore(Long userId, Long pointId, Long score, List<Long> fids) {

        List<UserPointEntity> topScore = userPointRepository.getTopScore(pointId, fids);
        Long userPosition = userPointRepository.getTopScoreUserPosition(score, pointId, fids, userId);

        return scoreMapper.toDto(
                userId,
                getPlace1Uid(topScore, 0),
                getPlace1Uid(topScore, 1),
                getPlace1Uid(topScore, 2),
                userPosition,
                pointId
        );
    }

    private static Long getPlace1Uid(List<UserPointEntity> topScore, int index) {
        return topScore.size() > index ? topScore.get(index).getId().getUserId() : null;
    }

    @Override
    public GotStuffRsDto getUserStuffRsDto(Long userId) {
        Optional<UserStuffEntity> userStuff = userStuffRepository.findById(userId);
        if (userStuff.isEmpty()) {
            userStuffRepository.creatUserStuff(userId);
            userStuff = userStuffRepository.findById(userId);
            //@todo-a just get?
        }
        return stuffMapper.entityToDto(userStuff.get());
    }

    private void updateUserPoint(Long userId, Long pointId, Long score) {
        commonSender.statistic(userId, StatisticEnum.ID_FINISH_PLAY, pointId.toString(), score.toString());
        userPointRepository.updateUserPoint(userId, pointId, score);
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

        stuffService.sendStuffToUser(userId);

        //@todo-a transaction control!
        commonSender.statistic(userId, StatisticEnum.ID_OPEN_CHEST, chestId.toString());
    }

    @Override
    public GotStuffRsDto spendCoinsForTurns(Long userId) {
        var product = ShopStore.turnsUp;
        var userStuff = stuffService.getUserStuff(userId);
        System.out.println(userStuff);
        //@todo-a find any System.out.println();
        decrementStuff(userStuff, product.getPriceGold(), ObjectEnum.STUFF_GOLD);

        userStuffRepository.save(userStuff);

        commonSender.statistic(userId, StatisticEnum.ID_BUY_TURNS);
        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public UpdateUserInfoRsDto buyHealth(Long userId, Long index) {
        var userEntity = getUserById(userId);
        var userStuff = stuffService.getUserStuff(userId);

        if (UserHelper.getHealths(userEntity) > 0) {
            commonSender.log(userId, "The user has more than zero lives.", ClientLogLevels.WARN, true);
        }
        decrementStuff(userStuff, ShopStore.health.getPriceGold(), ObjectEnum.STUFF_GOLD);
        UserHelper.setMaxHealth(userEntity);

        userRepository.save(userEntity);
        userStuffRepository.save(userStuff);

        commonSender.log(userId, "Игрок " + userId + " купил жизней❤❤❤❤❤ " + userStuff, ClientLogLevels.INFO, true);
        commonSender.statistic(userId, ID_BUY_HEALTH);

        stuffService.sendStuffToUser(userId);

        return mapMapper.entityToDto(userEntity);
    }

    @Override
    public GotStuffRsDto buyProduct(Long userId, Long index, ObjectEnum objectId) {
        var product = ShopStore.getProduct(index, objectId);
        UserStuffEntity userStuff = stuffService.getUserStuff(userId);

        decrementStuff(userStuff, product.getPriceGold(), ObjectEnum.STUFF_GOLD);
        incrementStuff(userStuff, product);

        userStuffRepository.save(userStuff);

        commonSender.statistic(userId, getStatIdFromObjectId(product));
        commonSender.log(userId,
                "Игрок " + userId + " купил " + product.getObjectEnum().getComment() +
                        ", теперь: " + userStuff.getQuantityByObject(product.getObjectEnum()) +
                        ", 💰 " + userStuff.getGoldQty(),
                ClientLogLevels.INFO, true);

        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public GotStuffRsDto spendMagic(Long userId, ObjectEnum objectId) {
        var userStuff = stuffService.getUserStuff(userId);

        decrementStuff(userStuff, 1L, objectId);

        commonSender.statistic(userId, getUsedStatFromObject(objectId));
        commonSender.log(userId, "Игрок " + userId + " использовал " + objectId.getComment() +
                " теперь " + userStuff.toString(), ClientLogLevels.INFO, true);

        return stuffMapper.entityToDto(userStuff);
    }

    @Override
    public void exitGame(Long userId, Long pointId) {
        commonSender.statistic(userId, ID_EXIT_GAME, pointId.toString());
    }

    @Override
    public void looseGame(Long userId, Long pointId) {
        commonSender.statistic(userId, ID_LOOSE_GAME, pointId.toString());
    }

    private static StatisticEnum getStatIdFromObjectId(ProductDto product) {
        //@todo-a may be one statisitc BUY_ITEM with param id product?
        return switch (product.getObjectEnum()) {
            case STUFF_HUMMER -> StatisticEnum.ID_BUY_HUMMER;
            case STUFF_LIGHTNING -> StatisticEnum.ID_BUY_LIGHTNING;
            case STUFF_SHUFFLE -> StatisticEnum.ID_BUY_SHUFFLE;
            default -> throw new RuntimeException("No found stat for objectId");
        };
    }

    private static void incrementStuff(UserStuffEntity stuff, ProductDto product) {
        switch (product.getObjectEnum()) {
            case STUFF_HUMMER -> stuff.setHummerQty(stuff.getHummerQty() + product.getQuantity());
            case STUFF_LIGHTNING -> stuff.setLightningQty(stuff.getLightningQty() + product.getQuantity());
            case STUFF_SHUFFLE -> stuff.setShuffleQty(stuff.getShuffleQty() + product.getQuantity());
            case STUFF_GOLD -> stuff.setGoldQty(stuff.getGoldQty() + product.getQuantity());
            default -> throw new RuntimeException("Product not found.");
        }
    }

    private static void decrementStuff(UserStuffEntity stuff, Long quality, ObjectEnum objectId) {
        System.out.println("stuff" + stuff);
        // universalization may be?
        switch (objectId) {
            case STUFF_HUMMER -> stuff.setHummerQty(stuff.getHummerQty() - quality);
            case STUFF_LIGHTNING -> stuff.setLightningQty(stuff.getLightningQty() - quality);
            case STUFF_SHUFFLE -> stuff.setShuffleQty(stuff.getShuffleQty() - quality);
            case STUFF_GOLD -> stuff.setGoldQty(stuff.getGoldQty() - quality);
        }
    }

    private static StatisticEnum getUsedStatFromObject(ObjectEnum objectId) {
        return switch (objectId) {
            case STUFF_HUMMER -> ID_HUMMER_USE;
            case STUFF_LIGHTNING -> ID_LIGHTNING_USE;
            case STUFF_SHUFFLE -> ID_SHUFFLE_USE;
            default -> throw new RuntimeException("Not found stat for objectId");
        };
    }

    private void isUserExistsOrThrowException(Long userId) {
        //@todo-a method just call method
        getUserById(userId);
    }

    private UserEntity getUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}
