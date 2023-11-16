package m3.gameplay.dto;

import lombok.*;
import m3.gameplay.enums.DataObjects;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class PrizeDto {

    private DataObjects id;
    private Long count;
}
