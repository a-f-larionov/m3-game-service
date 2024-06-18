package m3.game.services.webmvc;

import m3.game.controllers.PaymentController;
import m3.game.dto.vk.rq.VKBuyRqBodyDto;
import m3.game.mappers.VKBuyMapper;
import m3.game.services.PaymentService;
import m3.lib.dto.ProductDto;
import m3.lib.kafka.sender.CommonSender;
import m3.lib.store.ShopStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@ActiveProfiles("test")
public class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;
    @MockBean
    private CommonSender commonSender;
    @MockBean
    private VKBuyMapper vkBuyMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void vkBuyTestPost() throws Exception {
        // given
        var appId = 123456789L;
        var sig = "29247ba16aee29ed41c082225f7c9965";
        var socNetUserId = 120L;
        var product = ShopStore.gold.get(2);
        var extOrderId = 200L;
        var notificationType = "order_status_change_test";
        var status = "chargeable";
        Map<String, String> body = createMapBody(appId, socNetUserId, sig, extOrderId, product, notificationType, status);

        when(vkBuyMapper.bodyToDto(any())).thenReturn(buildVkRqBodyDto(product, socNetUserId, appId, notificationType, extOrderId, sig, status));

        // when
        mvc.perform(MockMvcRequestBuilders
                        .post("/payments/vk_buy")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content(createRequestContent(appId, socNetUserId, sig, extOrderId, product, notificationType, status)))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        verify(paymentService).vkBuy(
                eq(appId),
                eq(socNetUserId), eq(sig),
                eq(product.getPriceVotes()),
                eq(extOrderId),
                eq(notificationType),
                eq(status),
                eq(body));
    }

    private static Map<String, String> createMapBody(long appId, long socNetUserId, String sig, long extOrderId, ProductDto product, String notificationType, String status) {
        return Map.of(
                "app_id", String.valueOf(appId),
                "receiver_id", String.valueOf(socNetUserId),
                "sig", sig,
                "order_id", String.valueOf(extOrderId),
                "item_price", String.valueOf(product.getPriceVotes()),
                "notification_type", notificationType,
                "status", status
        );
    }

    private static String createRequestContent(long appId, long socNetUserId, String sig, long extOrderId, ProductDto product, String notificationType, String status) {
        return "app_id=" + appId +
                "&receiver_id=" + socNetUserId +
                "&sig=" + sig +
                "&order_id=" + extOrderId +
                "&item_price=" + product.getPriceVotes() +
                "&notification_type=" + notificationType +
                "&status=" + status;
    }

    private static VKBuyRqBodyDto buildVkRqBodyDto(ProductDto product, long socNetUserId, long appId, String notificationType, long extOrderId, String sig, String status) {
        return VKBuyRqBodyDto.builder()
                .appId(appId)
                .date(123123L)
                .item_id("")
                .item_photo_url("")
                .item_title("3 VK voutes")
                .itemPrice(product.getPriceVotes())
                .orderId(extOrderId)
                .socNetUserId(socNetUserId)
                .notificationType(notificationType)
                .status(status)
                .user_id(socNetUserId)
                .sig(sig)
                .build();
    }
}
