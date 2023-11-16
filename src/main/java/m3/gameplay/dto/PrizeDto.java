package m3.gameplay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import m3.gameplay.enums.DataObjects;

@AllArgsConstructor
@Getter
@Setter
public class PrizeDto {

    private DataObjects id;
    private Long count;
}
