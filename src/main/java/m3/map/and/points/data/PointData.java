package m3.map.and.points.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import m3.map.and.points.enums.DataObjects;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class PointData {

    private final Long id;
    private final Long turns;
    private final Long score1;
    private final Long score2;
    private final Long score3;
    private final Map<DataObjects, Integer> goals;
    private final List<String> mask;
    private final List<String> gems;
    private final List<List<String>> special;
}
