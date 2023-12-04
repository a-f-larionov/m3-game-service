package m3.game.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class ChestDto {

    private Long id;
    private List<PrizeDto> prizes;
}
