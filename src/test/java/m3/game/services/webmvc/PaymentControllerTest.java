package m3.game.services.webmvc;

import m3.game.controllers.SharedController;
import m3.game.services.PaymentService;
import m3.lib.store.ShopStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SharedController.class)
public class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService = null;

    @Autowired
    private MockMvc mvc;

    @Test
    void vkBuyTest() throws Exception {
        // given
        var appId = 123456789L;
        var sig = "58387ab03c8868925d738e8ca9d1d169";
        var socNetUserId = 120L;
        var product = ShopStore.gold.get(2);
        var extOrderId = 200L;
        var notificationType = "order_status_change_test";
        var status = "chargeable";

        // when
        mvc.perform(MockMvcRequestBuilders
                        .get("/service/vk_buy" +
                                "?app_id=" + appId +
                                "&receiver_id=" + socNetUserId +
                                "&sig=" + sig +
                                "&order_id=" + extOrderId +
                                "&item_price=" + product.getPriceVotes() +
                                "&notification_type=" + notificationType +
                                "&status=" + status)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


        // then
        verify(paymentService).vkBuy(appId, socNetUserId, sig, product.getPriceVotes(), extOrderId, notificationType, status);
    }
}
