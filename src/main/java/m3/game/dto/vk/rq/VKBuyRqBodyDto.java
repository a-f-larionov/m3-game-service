package m3.game.dto.vk.rq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class VKBuyRqBodyDto {

    @JsonProperty("app_id")
    private Long appId;
    @JsonProperty("receiver_id")
    private Long socNetUserId;
    @JsonProperty("sig")
    private String sig;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("item_price")
    private Long itemPrice;
    @JsonProperty("notification_type")
    private String notificationType;
    @JsonProperty("status")
    private String status;

    private Long date;
    private String item_id;
    private String item_photo_url;
    private String item_title;
    private Long user_id;
}