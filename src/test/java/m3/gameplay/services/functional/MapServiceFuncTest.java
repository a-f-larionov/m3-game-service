package m3.gameplay.services.functional;

import m3.gameplay.BaseSpringBootTest;
import m3.gameplay.dto.rs.GotScoresRsDto;
import m3.gameplay.dto.rs.GotStuffRsDto;
import m3.gameplay.dto.rs.ScoreRsDto;
import m3.gameplay.services.MapService;
import m3.lib.dto.rs.UpdateUserInfoRsDto;
import m3.lib.enums.ObjectEnum;
import m3.lib.repositories.UserRepository;
import m3.lib.repositories.UserStuffRepository;
import m3.lib.store.ShopStore;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "logging.level.org.hibernate=DEBUG",
        "logging.level.logger.org.apache.kafka=OFF",
        "logging.level.org.springframework.orm.jpa=DEBUG",
        "logging.level.org.springframework.transaction=DEBUG",
        "spring.jpa.show-sql=true"
})
public class MapServiceFuncTest extends BaseSpringBootTest {

    @Autowired
    MapService mapService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserStuffRepository userStuffRepository;
    @Autowired
    UserRepository userRepository;

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
        assertThat(rs).isEqualTo(expectedRs);
    }

    @Test
    void getUserStuffCreateNew() {
        // given
        Long userId = 101L;
        var expectedRs = buildGotStuffRSDto(userId, 500L, 2L, 1L, 1L);

        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);

        // when
        GotStuffRsDto factRs = mapService.getUserStuffRsDto(userId);

        // then
        assertThat(factRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, 500L, 2L, 1L, 1L);
    }

    @Test
    void getUserStuff() {
        // given
        Long userId = 102L;
        var expectedRs = buildGotStuffRSDto(userId, 1000L, 10L, 20L, 30L);
        setUserStuff(userId, 1000L, 10L, 20L, 30L);

        // when
        GotStuffRsDto factRs = mapService.getUserStuffRsDto(userId);

        // then
        assertThat(factRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, 1000L, 10L, 20L, 30L);
    }

    @Test
    void spendCoinsForTurns() {
        // given
        Long userId = 103L;
        var product = ShopStore.turnsUp;
        var expectedRs = buildGotStuffRSDto(userId, 1000L - product.getPriceGold(), 10L, 20L, 30L);
        setUserStuff(userId, 1000L, 10L, 20L, 30L);

        // when
        GotStuffRsDto actualRs = mapService.spendCoinsForTurns(userId);

        // then
        assertThat(actualRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, 1000L - product.getPriceGold(), 10L, 20L, 30L);
    }

    @Test
    void buyProduct() {
        // given
        Long userId = 104L;
        Long itemIndex = 1L;
        var product = ShopStore.getProduct(1L, ObjectEnum.STUFF_HUMMER);
        setUserStuff(userId, 5000L, 10L, 20L, 30L);
        var expectedRs = buildGotStuffRSDto(userId, 5000L - product.getPriceGold(), 16L, 20L, 30L);

        // when
        GotStuffRsDto actualRs = mapService.buyProduct(userId, itemIndex, ObjectEnum.STUFF_HUMMER);

        // then
        assertThat(actualRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, 4000L, 16L, 20L, 30L);
    }

    @Test
    void spendMagic() {
        // given
        Long userId = 105L;
        setUserStuff(userId, 5000L, 10L, 20L, 30L);
        var expectedRs = buildGotStuffRSDto(userId, 5000L, 9L, 20L, 30L);

        // when
        GotStuffRsDto actualRs = mapService.spendMagic(userId, ObjectEnum.STUFF_HUMMER);

        // then
        assertThat(actualRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, 5000L, 9L, 20L, 30L);
    }

    @Test
    void buyHealth() {
        // given
        var currenttime = (long) Math.floor((double) System.currentTimeMillis() / 1000);
        Long userId = 106L;
        Long itemIndex = 1L;
        var product = ShopStore.health;

        setUserStuff(userId, 10000L, 100L, 200L, 300L);
        deleteAllUsers();
        jdbcTemplate.update("INSERT INTO users (id, socNetUserId, socNetTypeId, create_tm, login_tm, logout_tm, nextPointId, fullRecoveryTime) " +
                "VALUES (?, 1, 2, 3, 4, 5, 6, 7)", userId);
        mapService.getUserStuffRsDto(userId);// for create stuff

        // when
        UpdateUserInfoRsDto actualRs = mapService.buyHealth(userId, itemIndex);

        // then
        assertThat(actualRs.getFullRecoveryTime()).isCloseTo(currenttime, Offset.offset(10L));
        assertDbUserStuff(userId, 10000L - product.getPriceGold(), 100L, 200L, 300L);

        Map<String, Object> dbState = jdbcTemplate.queryForMap("SELECT * FROM users WHERE id = ?", userId);
        Long fullRecoveryTime = (Long) dbState.get("fullRecoveryTime");
        assertThat(fullRecoveryTime).isCloseTo(currenttime, Offset.offset(10L));
    }

    private void deleteAllUsers() {
        jdbcTemplate.update("DELETE FROM users WHERE create_tm IS NOT NULL OR create_tm IS NULL");
    }

    private void setUserStuff(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);
        jdbcTemplate.update("INSERT INTO users_stuff (userId, goldQty, hummerQty, lightningQty, shuffleQty) " +
                "VALUE (?, ? ,? ,? ,?)", userId, gold, hummer, lightning, shuffle);
    }

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

    private void deleteAllUsersPoints() {
        jdbcTemplate.update("DELETE FROM users_points WHERE userId > 0");
    }

    private ScoreRsDto createScoreRsDto(long userId, long pointId, long score) {
        return ScoreRsDto.builder().userId(userId).pointId(pointId).score(score).build();
    }

    private GotStuffRsDto buildGotStuffRSDto(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        return GotStuffRsDto.builder()
                .userId(userId)
                .goldQty(gold)
                .hummerQty(hummer)
                .lightningQty(lightning)
                .shuffleQty(shuffle)
                .build();
    }

    private void assertDbUserStuff(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        Map<String, Object> dbState = jdbcTemplate.queryForMap("SELECT * FROM users_stuff WHERE userId = ?", userId);
        assertThat(dbState.get("userId")).isEqualTo(userId);
        assertThat(dbState.get("goldQty")).isEqualTo(gold);
        assertThat(dbState.get("hummerQty")).isEqualTo(hummer);
        assertThat(dbState.get("lightningQty")).isEqualTo(lightning);
        assertThat(dbState.get("shuffleQty")).isEqualTo(shuffle);
    }

}
