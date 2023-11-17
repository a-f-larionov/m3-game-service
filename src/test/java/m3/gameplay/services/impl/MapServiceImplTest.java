package m3.gameplay.services.impl;

import m3.gameplay.dto.ChestDto;
import m3.gameplay.dto.PrizeDto;
import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.GotStuffRsDto;
import m3.gameplay.dto.rs.ScoreRsDto;
import m3.gameplay.kafka.sender.CommonSender;
import m3.gameplay.mappers.MapMapper;
import m3.gameplay.mappers.ScoreMapper;
import m3.gameplay.mappers.StuffMapper;
import m3.gameplay.services.ChestsService;
import m3.gameplay.services.MapService;
import m3.gameplay.services.PointsService;
import m3.gameplay.services.StuffService;
import m3.lib.entities.UserEntity;
import m3.lib.entities.UserPointEntity;
import m3.lib.entities.UserStuffEntity;
import m3.lib.entities.UsersPointId;
import m3.lib.enums.StatisticEnum;
import m3.lib.repositories.UserPointRepository;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static m3.gameplay.enums.DataObjects.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MapServiceImplTest {
    private final PointsService pointsService = mock(PointsService.class);
    private final ChestsService chestsService = mock(ChestsService.class);
    private final StuffService stuffService = mock(StuffService.class);
    private final MapMapper mapMapper = mock(MapMapper.class);
    private final StuffMapper stuffMapper = mock(StuffMapper.class);
    private final ScoreMapper scoreMapper = mock(ScoreMapper.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserPointRepository userPointRepository = mock(UserPointRepository.class);
    private final UserStuffRepository userStuffRepository = mock(UserStuffRepository.class);
    private final CommonSender commonSender = mock(CommonSender.class);
    private final KafkaTemplate<String, Object> kafkaTemplate = mock(KafkaTemplate.class);

    private final MapService mapService = new MapServiceImpl(pointsService, chestsService, stuffService, mapMapper, stuffMapper, scoreMapper, userRepository, userPointRepository, userStuffRepository, commonSender, kafkaTemplate);

    @Test
    @Disabled
    void existsMap() {
        // given

        // when

        // then
    }

    @Test
    @Disabled
    void getById() {
        // given

        // when

        // then
    }

    @Test
    @Disabled
    void getMapInfo() {
        // given

        // when

        // then
    }

    @Test
    @Disabled
    void getScores() {
        // given
        Long userId = 100L;
        List<Long> uids = List.of(101L, 102L, 103L);
        List<Long> pids = List.of(1101L, 1102L, 1103L);

        var expectedRs = GotScoresRsDto.builder()
                .userId(userId)
                .rows(List.of(
                        ScoreRsDto.builder().userId(10L).pointId(20L).score(30L).build(),
                        ScoreRsDto.builder().userId(11L).pointId(21L).score(31L).build(),
                        ScoreRsDto.builder().userId(12L).pointId(22L).score(32L).build()
                ))
                .build();

        List<UserPointEntity> expectedEntitiesList = List.of(
                createUserPointEntity(101L, 1101L, 11101),
                createUserPointEntity(102L, 1102L, 11102),
                createUserPointEntity(103L, 1103L, 11103)
        );

        when(userPointRepository.getScores(eq(pids), eq(uids)))
                .thenReturn(expectedEntitiesList);
        when(scoreMapper.toDto(eq(userId), eq(expectedEntitiesList)))
                .thenReturn(expectedRs);

        // when
        GotScoresRsDto rs = mapService.getScores(userId, pids, uids);

        // then
        verify(userPointRepository).getScores(eq(pids), eq(uids));
        verify(scoreMapper).toDto(eq(userId), eq(expectedEntitiesList));
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    private static UserPointEntity createUserPointEntity(Long userId, Long pointId, Integer score) {
        return UserPointEntity.builder().id(UsersPointId.builder().userId(userId).pointId(pointId).build()).score(score).build();
    }

    @Test
    void gotPointTopScore() {
        // given
        Long userId = 100L;
        Long pointId = 200L;
        Long score = 300L;
        List<Long> fids = List.of(401L, 402L, 403L);
        var expectedPos = 1000L;
        List<UserPointEntity> expectedTopScore = List.of(
                UserPointEntity.builder().id(UsersPointId.builder().userId(1001L).build()).build(),
                UserPointEntity.builder().id(UsersPointId.builder().userId(1002L).build()).build(),
                UserPointEntity.builder().id(UsersPointId.builder().userId(1003L).build()).build()
        );

        var expectedRs = GotPointTopScoreRsDto.builder()
                .userId(userId)
                .build();

        when(userPointRepository.getTopScoreUserPosition(any(), any(), anyList(), any()))
                .thenReturn(expectedPos);

        when(userPointRepository.getTopScore(any(), anyList()))
                .thenReturn(expectedTopScore);

        when(scoreMapper.toDto(any(), any(), any(), any(), any(), any()))
                .thenReturn(expectedRs);

        // when
        GotPointTopScoreRsDto rs = mapService.getPointTopScore(userId, pointId, score, fids);

        // then
        verify(userPointRepository).getTopScore(eq(pointId), eq(fids));
        verify(userPointRepository).getTopScoreUserPosition(eq(score), eq(pointId), eq(fids), eq(userId));

        verify(scoreMapper).toDto(
                eq(userId),
                eq(expectedTopScore.get(0).getId().getUserId()),
                eq(expectedTopScore.get(1).getId().getUserId()),
                eq(expectedTopScore.get(2).getId().getUserId()),
                eq(expectedPos),
                eq(pointId)
        );

        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    @Test
    void onFinishWithNoUserExists() {
        // given
        Long userId = 10L;
        Long pointId = 20L;
        Long score = 30L;
        Long chestId = 0L; // zere - means no chest to open

        when(userRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when
        ThrowableAssert.ThrowingCallable callable = () -> {
            mapService.onFinish(userId, pointId, score, chestId);
        };

        // then
        Assertions.assertThatCode(callable)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
        verify(userRepository, times(1)).findById(eq(userId));
    }

    @Test
    void onFinishWithNoChest() {
        // given
        Long userId = 10L;
        Long pointId = 20L;
        Long score = 30L;
        Long chestId = 0L; // zere - means no chest to open

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(UserEntity.builder()
                        .id(userId)
                        .nextPointId(1L)
                        .build()));

        // when
        mapService.onFinish(userId, pointId, score, chestId);

        // then
        verifyNoInteractions(chestsService);
        verifyNoInteractions(stuffService);
        verifyNoInteractions(userStuffRepository);
        verifyNoInteractions(stuffMapper);
        verifyNoInteractions(kafkaTemplate);

        verify(commonSender).statistic(eq(userId), eq(StatisticEnum.ID_FINISH_PLAY), eq(pointId.toString()), eq(score.toString()));
        verify(commonSender).statistic(eq(userId), eq(StatisticEnum.ID_LEVEL_UP), eq(String.valueOf(pointId + 1)));

        verify(userRepository).findById(eq(userId));
        verify(userPointRepository).updateUserPoint(eq(userId), eq(pointId), eq(score));
        verify(userRepository).nextPointUp(eq(userId), eq(pointId + 1));
    }

    @Test
    void onFinishWithPrizes() {
        // given
        Long userId = 10L;
        Long pointId = 20L;
        Long score = 30L;
        Long chestId = 1L;

        var expectedStuffDto = GotStuffRsDto.builder()
                .goldQty(1000L)
                .hummerQty(2000L)
                .lightningQty(3000L)
                .shuffleQty(4000L)
                .build();
        var expectedStuffEntity = UserStuffEntity.builder()
                .id(1L)
                .goldQty(1001L)
                .hummerQty(1002L)
                .lightningQty(10003L)
                .shuffleQty(10004L)
                .build();

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(UserEntity.builder()
                        .id(userId)
                        .nextPointId(1L)
                        .build()));

        when(chestsService.getById(any()))
                .thenReturn(ChestDto.builder()
                        .id(chestId)
                        .prizes(List.of(
                                PrizeDto.builder().id(STUFF_GOLD).count(100L).build(),
                                PrizeDto.builder().id(STUFF_HUMMER).count(200L).build(),
                                PrizeDto.builder().id(STUFF_LIGHTNING).count(300L).build(),
                                PrizeDto.builder().id(STUFF_SHUFFLE).count(400L).build()
                        ))
                        .build());
        when(stuffMapper.entityToDto(any()))
                .thenReturn(expectedStuffDto);

        when(userStuffRepository.getByUserId(any()))
                .thenReturn(expectedStuffEntity);

        // when
        mapService.onFinish(userId, pointId, score, chestId);

        // then
        verify(chestsService).getById(eq(chestId));
        verify(stuffService).giveAHummer(eq(userId), eq(200L));
        verify(stuffService).giveALightning(eq(userId), eq(300L));
        verify(stuffService).giveAShuffle(eq(userId), eq(400L));
        verify(stuffService).giveAGold(eq(userId), eq(100L));

        verify(userStuffRepository).getByUserId(eq(userId));
        verify(userStuffRepository).getByUserId(userId);
        verify(stuffMapper).entityToDto(expectedStuffEntity);
        verify(kafkaTemplate).send(eq("topic-client"), eq(expectedStuffDto));

        verify(commonSender).statistic(eq(userId), eq(StatisticEnum.ID_FINISH_PLAY), eq(pointId.toString()), eq(score.toString()));
        verify(commonSender).statistic(eq(userId), eq(StatisticEnum.ID_LEVEL_UP), eq(String.valueOf(pointId + 1)));
        verify(commonSender).statistic(eq(userId), eq(StatisticEnum.ID_OPEN_CHEST), eq(String.valueOf(chestId)));

        verify(userRepository).findById(eq(userId));
        verify(userPointRepository).updateUserPoint(eq(userId), eq(pointId), eq(score));
        verify(userRepository).nextPointUp(eq(userId), eq(pointId + 1));
    }
}