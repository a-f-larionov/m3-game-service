package m3.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ChestDto {

    private Long id;
    private List<PrizeDto> prizes;
}