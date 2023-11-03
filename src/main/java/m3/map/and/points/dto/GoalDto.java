package m3.map.and.points.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import m3.map.and.points.enums.DataObjects;

@AllArgsConstructor
@Getter
@Setter
public class GoalDto {

    private DataObjects id;
    private int count;
}
