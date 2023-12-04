package m3.game.dto;

import lombok.*;
import m3.lib.enums.ObjectEnum;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class PrizeDto {

    private ObjectEnum id;
    private Long count;
}
