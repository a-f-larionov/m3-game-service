package m3.gameplay.services.functional;

import m3.gameplay.BaseSpringBootTest;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.GotStuffRsDto;
import m3.gameplay.dto.rs.ScoreRsDto;
import m3.gameplay.services.MapService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "logging.level.org.hibernate.SQL=TRACE"
})
public class MapServiceFuncTest extends BaseSpringBootTest {

    @Autowired
    MapService mapService;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
    void getScores() {
        // given
        Long userId = 100L;
        List<Long> uids = List.of(1001L, 1002L, 1003L);
        List<Long> pids = List.of(1101L, 1102L, 1103L);

        var expectedRs = GotScoresRsDto.builder()
                .userId(userId)
                .rows(List.of(
                        createScoreRsDto(1001L, 1101L, 1201L),
                        createScoreRsDto(1002L, 1102L, 1202L),
                        createScoreRsDto(1003L, 1103L, 1203L)
                ))
                .build();

        deleteAllUsersPoints();
        jdbcTemplate.update("INSERT INTO users_points " +
                "(userId, pointId, score)" +
                " VALUES" +
                " (1001, 1101, 1201), " +
                " (1002, 1102, 1202), " +
                " (1003, 1103, 1203) "
        );

        // when
        GotScoresRsDto rs = mapService.getScores(userId, pids, uids);

        // then
        assertThat(rs)
                .isEqualTo(expectedRs);
    }

    private void deleteAllUsersPoints() {
        jdbcTemplate.update("DELETE FROM users_points WHERE userId > 0");
    }

    @Test
    @Disabled
    void gotPointTopScore() {
    }

    @Test
    @Disabled
    void onFinishWithNoUserExists() {

    }

    @Test
    @Disabled
    void onFinishWithNoChest() {
    }

    @Test
    @Disabled
    void onFinishWithPrizes() {
    }

    private static ScoreRsDto createScoreRsDto(long userId, long pointId, long score) {
        return ScoreRsDto.builder().userId(userId).pointId(pointId).score(score).build();
    }

    @Test
    void getUserStuffCreateNew() {
        // given
        Long userId = 100L;
        var expectedRs = GotStuffRsDto.builder()
                .userId(userId)
                .goldQty(500L)
                .hummerQty(2L)
                .shuffleQty(1L)
                .lightningQty(1L)
                .build();

        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);

        // when
        GotStuffRsDto factRs = mapService.getUserStuff(userId);

        // then
        assertThat(factRs)
                .isEqualTo(expectedRs);

        Map<String, Object> dbState = jdbcTemplate.queryForMap("SELECT * FROM users_stuff WHERE userId = ?", userId);
        assertThat(dbState.get("userId")).isEqualTo(userId);
        assertThat(dbState.get("goldQty")).isEqualTo(500L);
        assertThat(dbState.get("hummerQty")).isEqualTo(2L);
        assertThat(dbState.get("shuffleQty")).isEqualTo(1L);
        assertThat(dbState.get("lightningQty")).isEqualTo(1L);
    }

    @Test
    void getUserStuff() {
        // given
        Long userId = 100L;
        var expectedRs = GotStuffRsDto.builder()
                .userId(userId)
                .goldQty(0L)
                .hummerQty(0L)
                .shuffleQty(0L)
                .lightningQty(0L)
                .build();

        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);
        jdbcTemplate.update("INSERT INTO users_stuff (userId, hummerQty, shuffleQty, goldQty, lightningQty) " +
                "VALUE (?, 0 ,0 ,0 ,0)", userId);

        // when
        GotStuffRsDto factRs = mapService.getUserStuff(userId);

        // then
        assertThat(factRs)
                .isEqualTo(expectedRs);

        Map<String, Object> dbState = jdbcTemplate.queryForMap("SELECT * FROM users_stuff WHERE userId = ?", userId);
        assertThat(dbState.get("userId")).isEqualTo(userId);
        assertThat(dbState.get("goldQty")).isEqualTo(0L);
        assertThat(dbState.get("hummerQty")).isEqualTo(0L);
        assertThat(dbState.get("shuffleQty")).isEqualTo(0L);
        assertThat(dbState.get("lightningQty")).isEqualTo(0L);
    }

}
