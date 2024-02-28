package m3.game.dto.rq;

import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rq.UserIdRqDto;
import org.hibernate.validator.constraints.Range;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BuyHummerRqDto extends UserIdRqDto {
    @Range(min = 0, max = 2)
    private Long index;
}
