package m3.gameplay.dto;

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
