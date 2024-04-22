package m3.game.dto.rq;


import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rq.UserIdRqDto;
import org.hibernate.validator.constraints.Range;

// @todo check every rqdto has validation?
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BuyHealthRqDto extends UserIdRqDto {
    @Range(min = 0, max = 2)
    private Long index;
}
