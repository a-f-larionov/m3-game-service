package m3.gameplay.listeners;

import m3.gameplay.dto.rq.OnFinishRqDto;
import m3.gameplay.dto.rq.SendMeMapInfoRqDto;
import m3.gameplay.dto.rq.SendMePointTopScoreRqDto;
import m3.gameplay.dto.rq.SendMeScoresRqDto;
import m3.gameplay.dto.rs.GotMapInfoRsDto;
import m3.gameplay.dto.rs.GotPointTopScoreRsDto;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.ScoreRsDto;
import m3.gameplay.services.MapService;
import m3.gameplay.store.MapStore;
import m3.gameplay.store.PointStore;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TopicGamePlayKafkaListenerTest {


    private final MapService mapService = mock(MapService.class);
    private final TopicGamePlayKafkaListener gameplayListener = new TopicGamePlayKafkaListener(mapService);

    @Test
    void sendMeMapInfoRqDto() {
        // given
        var rq = SendMeMapInfoRqDto.builder()
                .userId(1L)
                .mapId(2L)
                .build();
        var expectedRs = GotMapInfoRsDto.builder()
                .userId(1L)
                .points(List.of(PointStore.points.get(0)))
                .map(MapStore.maps.get(0))
                .build();
        when(mapService.getMapInfo(any(), any()))
                .thenReturn(expectedRs);

        // when
        GotMapInfoRsDto rs = gameplayListener.sendMeMapInfoRqDto(rq);

        // then
        verify(mapService).getMapInfo(rq.getUserId(), rq.getMapId());
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    @Test
    void sendMeScoresRqDto() {
        // given
        SendMeScoresRqDto rq = SendMeScoresRqDto.builder()
                .userId(10L)
                .pids(List.of(20L, 21L))
                .uids(List.of(30L, 31L))
                .build();
        var expectedRs = GotScoresRsDto.builder()
                .userId(1L)
                .rows(List.of(ScoreRsDto.builder()
                        .userId(100L)
                        .pointId(200L)
                        .score(300L)
                        .build()))
                .build();

        when(mapService.getScores(any(), any(), any()))
                .thenReturn(expectedRs);

        // when
        GotScoresRsDto rs = gameplayListener.sendMeScoresRqDto(rq);

        // then
        verify(mapService).getScores(eq(rq.getUserId()), eq(rq.getPids()), eq(rq.getUids()));
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    @Test
    void sendMePointTopScore() {
        // given
        SendMePointTopScoreRqDto rq = SendMePointTopScoreRqDto.builder()
                .userId(10L)
                .pointId(20L)
                .score(30L)
                .fids(List.of(40L, 41L))
                .build();
        var expectedRs = GotPointTopScoreRsDto.builder()
                .userId(1L)
                .pointId(20L)
                .pos(30L)
                .place1Uid(100L)
                .place2Uid(200L)
                .place3Uid(300L)
                .build();

        when(mapService.getPointTopScore(any(), any(), any(), any()))
                .thenReturn(expectedRs);

        // when
        GotPointTopScoreRsDto rs = gameplayListener.sendMePointTopScore(rq);

        // then
        verify(mapService).getPointTopScore(eq(rq.getUserId()), eq(rq.getPointId()), eq(rq.getScore()), eq(rq.getFids()));
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    @Test
    void onFinish() {
        // given
        OnFinishRqDto rq = OnFinishRqDto.builder()
                .userId(10L)
                .pointId(20L)
                .score(30L)
                .chestId(40L)
                .build();

        // when
        gameplayListener.onFinish(rq);

        // then
        verify(mapService).onFinish(
                eq(rq.getUserId()),
                eq(rq.getPointId()),
                eq(rq.getScore()),
                eq(rq.getChestId())
        );
    }
}