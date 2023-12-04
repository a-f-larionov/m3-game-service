package m3.game.listeners;

import m3.game.dto.rq.*;
import m3.game.dto.rs.*;
import m3.game.services.MapService;
import m3.game.services.PaymentService;
import m3.game.store.MapStore;
import m3.game.store.PointStore;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.enums.ObjectEnum;
import m3.lib.enums.SocNetType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TopicGameKafkaListenerTest {

    private final MapService mapService = mock(MapService.class);
    private final PaymentService paymentService = mock(PaymentService.class);
    private final TopicGameKafkaListener gameListener = new TopicGameKafkaListener(mapService, paymentService);

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
        GotMapInfoRsDto rs = gameListener.sendMeMapInfoRqDto(rq);

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
        GotScoresRsDto rs = gameListener.sendMeScoresRqDto(rq);

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
        GotPointTopScoreRsDto rs = gameListener.sendMePointTopScore(rq);

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
        gameListener.onFinish(rq);

        // then
        verify(mapService).onFinish(
                eq(rq.getUserId()),
                eq(rq.getPointId()),
                eq(rq.getScore()),
                eq(rq.getChestId())
        );
    }

    @Test
    void sendMeStuff() {
        // given
        Long userId = 100L;
        SendMeStuffRqDto rq = SendMeStuffRqDto.builder()
                .userId(userId)
                .build();
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.getUserStuffRsDto(any())).thenReturn(expectedRs);

        // when
        GotStuffRsDto rs = gameListener.sendMeStuff(rq);

        // then
        verify(mapService).getUserStuffRsDto(eq(rq.getUserId()));
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    @Test
    void spendCoinsForTurnsRqDto() {
        // given
        var userId = 100L;
        SpendCoinsForTurnsRqDto rq = SpendCoinsForTurnsRqDto.builder()
                .userId(userId)
                .build();
        GotStuffRsDto expectedRsDto = buildGotStuffRsDto(userId);
        when(mapService.spendCoinsForTurns(any())).thenReturn(expectedRsDto);

        // when
        GotStuffRsDto actualRs = gameListener.spendCoinsForTurnsRqDto(rq);

        // then
        assertThat(actualRs).isEqualTo(expectedRsDto);
        verify(mapService).spendCoinsForTurns(eq(rq.getUserId()));
    }

    @Test
    void buyHealth() {
        // given
        var userId = 100L;
        var index = 200L;
        var rq = BuyHealthRqDto.builder().userId(userId).index(index).build();
        var expectedRs = UpdateUserInfoRsDto.builder().userId(userId).fullRecoveryTime(123L).build();
        when(mapService.buyHealth(any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.buyHealth(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .buyHealth(userId, index);
    }

    @Test
    void buyHummer() {
        // given1
        var userId = 100L;
        var index = 200L;
        var rq = buildBuyHummerRqDto(userId, index);
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.buyProduct(any(), any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.buyHummer(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .buyProduct(userId, index, ObjectEnum.STUFF_HUMMER);
    }

    @Test
    void buyLightning() {
        // given1
        var userId = 100L;
        var index = 200L;
        var rq = buildBuyLightningRqDto(userId, index);
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.buyProduct(any(), any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.buyLightning(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .buyProduct(userId, index, ObjectEnum.STUFF_LIGHTNING);
    }

    @Test
    void buyShuffle() {
        // given1
        var userId = 100L;
        var index = 200L;
        var rq = buildBuyShuffleRqDto(userId, index);
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.buyProduct(any(), any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.buyShuffle(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .buyProduct(userId, index, ObjectEnum.STUFF_SHUFFLE);
    }

    @Test
    void usedHummer() {
        // given
        var userId = 100L;
        var rq = UsedHummerRqDto.builder().userId(userId).build();
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.spendMagic(any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.usedHummer(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .spendMagic(userId, ObjectEnum.STUFF_HUMMER);
    }

    @Test
    void usedLightning() {
        // given
        var userId = 100L;
        var rq = UsedLightningRqDto.builder().userId(userId).build();
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.spendMagic(any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.usedLightning(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .spendMagic(userId, ObjectEnum.STUFF_LIGHTNING);
    }

    @Test
    void usedShuffle() {
        // given
        var userId = 100L;
        var rq = UsedShuffleRqDto.builder().userId(userId).build();
        var expectedRs = buildGotStuffRsDto(userId);
        when(mapService.spendMagic(any(), any())).thenReturn(expectedRs);

        // when
        var actualRs = gameListener.usedShuffle(rq);

        // then
        assertThat(actualRs)
                .isEqualTo(expectedRs);
        verify(mapService)
                .spendMagic(userId, ObjectEnum.STUFF_SHUFFLE);
    }

    @Test
    void doOrderChange() {
        // given
        var tid = 100L;
        Long socNetUserId = 300L;
        Long extOrderId = 200L;
        Long itemPrice = 400L;
        var rq = DoOrderChangeRqDto.builder()
                .tid(tid)
                .receiverId(socNetUserId)
                .orderId(extOrderId)
                .itemPrice(itemPrice)
                .socNetType(SocNetType.VK)
                .build();

        // when
        gameListener.doOrderChange(rq);

        // then
        verify(paymentService).doOrderChange(
                eq(tid),
                eq(SocNetType.VK),
                eq(socNetUserId),
                eq(itemPrice),
                eq(extOrderId)
        );
    }

    private static GotStuffRsDto buildGotStuffRsDto(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        return GotStuffRsDto.builder()
                .userId(userId)
                .goldQty(gold)
                .hummerQty(hummer)
                .shuffleQty(shuffle)
                .lightningQty(lightning)
                .build();
    }

    private static GotStuffRsDto buildGotStuffRsDto(long userId) {
        return buildGotStuffRsDto(userId, 100L, 1L, 1L, 1L);
    }

    private static BuyHummerRqDto buildBuyHummerRqDto(long userId, long index) {
        return BuyHummerRqDto.builder().userId(userId).index(index).build();
    }

    private static BuyLightningRqDto buildBuyLightningRqDto(long userId, long index) {
        return BuyLightningRqDto.builder().userId(userId).index(index).build();
    }

    private static BuyShuffleRqDto buildBuyShuffleRqDto(long userId, long index) {
        return BuyShuffleRqDto.builder().userId(userId).index(index).build();
    }
}