package m3.gameplay.services.functional;

import m3.gameplay.BaseSpringBootTest;
import m3.gameplay.dto.rs.DoOrderChangeAnswerRsDto;
import m3.gameplay.dto.vk.rs.VKResponseDoOrderSuccessRsDto;
import m3.gameplay.services.PaymentService;
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
        Long tid = 10L;
        Long socNetUserId = 110L;
        Long orderId = 200L;
        var product = ShopStore.gold.get(1);
        SocNetType socNetType = SocNetType.VK;

        var expectedRs = DoOrderChangeAnswerRsDto.builder()
                .tid(tid)
                .response(VKResponseDoOrderSuccessRsDto.builder()
                        .orderId(orderId)
                        .build())
                .build();

        deleteAllUsers();
        deleteAllPayments();

        Long userId = createUserPartially(socNetType, socNetUserId);
        setUserStuff(userId, 100L, 10L, 20L, 30L);

        // when
        var actualRs = paymentService.doOrderChange(tid, socNetUserId, orderId, product.getPriceVotes(), socNetType);

        // then
        var paymentId = paymentRepository.findByOrderId(orderId).get().getId();
        VKResponseDoOrderSuccessRsDto response = (VKResponseDoOrderSuccessRsDto) (expectedRs.getResponse());
        response.setAppOrderId(paymentId);

        assertThat(actualRs)
                .isEqualTo(expectedRs);
        assertDbUserStuff(userId, 100L + product.getQuantity(), 10L, 20L, 30L);
        assertDbPaymentOrder(paymentId, userId, product.getPriceVotes(), orderId);
    }

    @Test
    public void standaloneBuy() {
        // given
        Long itemPrice = 1L; //
        var product = ShopStore.getGoldProductByPrice(itemPrice);
        Long socNetUserId = 100L;
        Long orderId = 123L;

        // when
        paymentService.standaloneBuy(socNetUserId, orderId, itemPrice);

        // then

    }

    @Test
    public void vkBuy() {
        // given
        Long itemPrice = 1L; //
        var product = ShopStore.getGoldProductByPrice(itemPrice);
        Long appId = 1234L;
        Long socNetUserId = 100L;
        Long orderId = 123L;
        String status = "recharcheagle";
        String notificationType = "notification type";
        String sig = "some sign here";

        // when
        paymentService.vkBuy(appId, socNetUserId, sig, orderId, itemPrice, notificationType, status);

        // then

    }

    private Long createUserPartially(SocNetType socNetType, Long socNetUserId) {
        jdbcTemplate.update("INSERT INTO users (socNetTypeId, socNetUserId) VALUES " +
                "(?, ?)", socNetType.getId(), socNetUserId);
        Long userId = (Long) jdbcTemplate.queryForMap("SELECT id FROM users WHERE socNetTypeId = ? AND socNetUserId = ? ", socNetType.getId(), socNetUserId).get("ID");
        return userId;
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

    private void deleteAllPayments() {
        jdbcTemplate.update("DELETE FROM payments WHERE id > 0");
    }

    private void deleteAllUsers() {
        jdbcTemplate.update("DELETE FROM users WHERE create_tm IS NOT NULL OR create_tm IS NULL");
    }

    private void setUserStuff(Long userId, Long gold, Long hummer, Long lightning, Long shuffle) {
        jdbcTemplate.update("DELETE FROM users_stuff WHERE userId = ?", userId);
        jdbcTemplate.update("INSERT INTO users_stuff (userId, goldQty, hummerQty, lightningQty, shuffleQty) " +
                "VALUE (?, ? ,? ,? ,?)", userId, gold, hummer, lightning, shuffle);
    }
}
