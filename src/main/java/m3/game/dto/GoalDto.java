package m3.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import m3.lib.enums.ObjectEnum;

@AllArgsConstructor
@Getter
@Setter
public class GoalDto {

    private ObjectEnum id;
    private int count;
}
