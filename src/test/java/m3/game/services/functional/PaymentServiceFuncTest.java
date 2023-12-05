package m3.game.services.functional;

import m3.game.BaseSpringBootTest;
import m3.game.dto.rs.DoOrderChangeAnswerRsDto;
import m3.game.dto.vk.rs.VKResponseDoOrderSuccessRsDto;
import m3.game.services.PaymentService;
import m3.lib.enums.SocNetType;
import m3.lib.repositories.PaymentRepository;
import m3.lib.store.ShopStore;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "logging.level.org.hibernate=DEBUG",
        "logging.level.logger.org.apache.kafka=OFF",
        "logging.level.org.springframework.orm.jpa=DEBUG",
        "logging.level.org.springframework.transaction=DEBUG",
        "spring.jpa.show-sql=true"
})
public class PaymentServiceFuncTest extends BaseSpringBootTest {
    @Autowired
    PaymentService paymentService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void doOrderChange() {
        // given
        var reqId = 10L;
        var socNetType = SocNetType.VK;
        var socNetUserId = 110L;
        var product = ShopStore.gold.get(1);
        var extOrderId = 200L;

        var expectedRs = buildDoOrderChangeRsDto(reqId, extOrderId);

        dbDeleteAllUsers();
        dbDeleteAllPayments();
        var userId = dbCreateUser(socNetType, socNetUserId);
        dbSetUserStuff(userId, 0L, 10L, 20L, 30L);


        // when
        var actualRs = paymentService.doOrderChange(reqId, socNetType, socNetUserId, product.getPriceVotes(), extOrderId);

        // then
        var paymentId = paymentRepository.findByOrderId(extOrderId).get().getId();
        ((VKResponseDoOrderSuccessRsDto) (expectedRs.getResponse())).setAppOrderId(paymentId);

        assertThat(actualRs).isEqualTo(expectedRs);
        assertDbUserStuff(userId, product.getQuantity(), 10L, 20L, 30L);
        assertDbPaymentOrder(paymentId, userId, product.getPriceVotes(), extOrderId);
    }

    @Test
    public void standaloneBuy() {
        // given
        var reqId = 1L;
        var socNetType = SocNetType.Standalone;
        var socNetUserId = 120L;
        var product = ShopStore.gold.get(2);
        var extOrderId = 200L;

        var expectedRs = buildDoOrderChangeRsDto(reqId, extOrderId);

        dbDeleteAllUsers();
        dbDeleteAllPayments();
        var userId = dbCreateUser(socNetType, socNetUserId);
        dbSetUserStuff(userId, 0L, 100L, 200L, 300L);

        // when
        var actualRs = paymentService.standaloneBuy(socNetUserId, product.getPriceVotes(), extOrderId);

        // then
        var paymentId = paymentRepository.findByOrderId(extOrderId).get().getId();
        ((VKResponseDoOrderSuccessRsDto) (expectedRs.getResponse())).setAppOrderId(paymentId);

        assertThat(actualRs.getTid()).isCloseTo(10L, Offset.offset(10L));
        assertThat(actualRs.getResponse()).isEqualTo(expectedRs.getResponse());
        assertDbUserStuff(userId, product.getQuantity(), 100L, 200L, 300L);
        assertDbPaymentOrder(paymentId, userId, product.getPriceVotes(), extOrderId);
    }

    @Test
    public void vkBuy() {
        // given
        var appId = 123456789L;
        var sig = "29247ba16aee29ed41c082225f7c9965";
        var reqId = 1L;
        var socNetType = SocNetType.VK;
        var socNetUserId = 120L;
        var product = ShopStore.gold.get(2);
        var extOrderId = 200L;
        var notificationType = "order_status_change_test";
        var status = "chargeable";
        Map<String, String> body = Map.of("key", "value");

        var expectedRs = buildDoOrderChangeRsDto(reqId, extOrderId);

        dbDeleteAllUsers();
        dbDeleteAllPayments();
        var userId = dbCreateUser(socNetType, socNetUserId);
        dbSetUserStuff(userId, 0L, 10L, 20L, 30L);

        // when

        var actualRs = paymentService.vkBuy(appId, socNetUserId, sig, product.getPriceVotes(), extOrderId, notificationType, status, body);

        // then
        assertThat(actualRs.getResponse()).isInstanceOf(VKResponseDoOrderSuccessRsDto.class);
        var paymentId = paymentRepository.findByOrderId(extOrderId).get().getId();
        ((VKResponseDoOrderSuccessRsDto) (expectedRs.getResponse())).setAppOrderId(paymentId);

        assertThat(actualRs.getTid()).isCloseTo(10L, Offset.offset(10L));
        assertThat(actualRs.getResponse()).isEqualTo(expectedRs.getResponse());
        assertDbUserStuff(userId, product.getQuantity(), 10L, 20L, 30L);
        assertDbPaymentOrder(paymentId, userId, product.getPriceVotes(), extOrderId);
    }

    private void assertDbUserStuff(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        Map<String, Object> dbState = jdbcTemplate.queryForMap("SELECT * FROM users_stuff WHERE userId = ?", userId);
        assertThat(dbState.get("userId")).isEqualTo(userId);
        assertThat(dbState.get("goldQty")).isEqualTo(gold);
        assertThat(dbState.get("hummerQty")).isEqualTo(hummer);
        assertThat(dbState.get("lightningQty")).isEqualTo(lightning);
        assertThat(dbState.get("shuffleQty")).isEqualTo(shuffle);
    }

    private void assertDbPaymentOrder(Long id, Long userId, Long itemPrice, Long orderId) {
        Map<String, Object> data = jdbcTemplate.queryForMap("SELECT * FROM payments WHERE id = ? ", id);
        assertThat((Long) data.get("TIME")).isCloseTo(System.currentTimeMillis() / 1000, Offset.offset(10L));
        assertThat((Long) data.get("USERID")).isEqualTo(userId);
        assertThat((Long) data.get("ITEMPRICE")).isEqualTo(itemPrice);
        assertThat((Long) data.get("ORDERID")).isEqualTo(orderId);
    }

    private void dbDeleteAllPayments() {
        jdbcTemplate.update("DELETE FROM payments WHERE id > 0");
    }

    private Long dbCreateUser(SocNetType socNetType, Long socNetUserId) {
        jdbcTemplate.update("INSERT INTO users (socNetTypeId, socNetUserId) VALUES " +
                "(?, ?)", socNetType.getId(), socNetUserId);
        Long userId = (Long) jdbcTemplate.queryForMap("SELECT id FROM users WHERE socNetTypeId = ? AND socNetUserId = ? ", socNetType.getId(), socNetUserId).get("ID");
        return userId;
    }

    private void dbDeleteAllUsers() {
        jdbcTemplate.update("DELETE FROM users WHERE create_tm IS NOT NULL OR create_tm IS NULL");
    }

    private void dbSetUserStuff(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);
        jdbcTemplate.update("INSERT INTO users_stuff (userId, goldQty, hummerQty, lightningQty, shuffleQty) " +
                "VALUE (?, ? ,? ,? ,?)", userId, gold, hummer, lightning, shuffle);
    }

    private static DoOrderChangeAnswerRsDto buildDoOrderChangeRsDto(Long tid, Long orderId) {
        return DoOrderChangeAnswerRsDto.builder()
                .tid(tid)
                .response(VKResponseDoOrderSuccessRsDto.builder()
                        .orderId(orderId)
                        .build())
                .build();
    }

}
