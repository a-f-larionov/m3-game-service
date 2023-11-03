package m3.map.and.points.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import m3.map.and.points.enums.DataObjects;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PointDto {

    public PointDto(@NonNull Long id,
                    @NonNull Long turns,
                    @NonNull Long score1, @NonNull Long score2, @NonNull Long score3,
                    @NonNull Map<DataObjects, Integer> goals,
                    @NonNull List<String> mask,
                    @NonNull List<String> gems,
                    @NonNull List<List<String>> special) {

        this.id = id;
        this.turns = turns;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.goals = goals.entrySet().stream()
                .map(e -> new GoalDto(e.getKey(), e.getValue()))
                .toList();
        this.layers = new Layers(mask, gems, special);
    }

    private final Long id;
    private final Long turns;
    private final Long score1;
    private final Long score2;
    private final Long score3;
    private final List<GoalDto> goals;
    private final Layers layers;

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Layers {

        @NonNull
        private final List<String> mask;
        @NonNull
        private final List<String> gems;
        @NonNull
        private final List<List<String>> special;
    }
}
