package m3.game.services.impl;

import m3.game.services.PaymentService;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentServiceImplTest {
    private final PaymentService paymentService = new PaymentServiceImpl(
            null,
            null,
            null,
            null,
            null);

    @Test
    void checkVKSign() {

        Map<String, String> params = new LinkedHashMap<>();

        params.put("app_id", "7506736");
        params.put("date", "1701801663");
        params.put("item", "");
        params.put("item_id", "");
        params.put("item_photo_url", "");
        params.put("item_price", "3");
        params.put("item_title", "3 VK voutes");
        params.put("notification_type", "order_status_change_test");
        params.put("order_id", "2148541");
        params.put("receiver_id", "12578187");
        params.put("status", "chargeable");
        params.put("user_id", "12578187");
        params.put("sig", "f104ad36c3eabb0220178a94cfee8f47");

        String expectedSig = "8c53ebdd4b5cc23a881c0aad5bacb342";

        // when
        String actualSig = paymentService.calcVKSign(params);

        // then
        assertThat(actualSig)
                .isEqualTo(expectedSig);
    }
}
