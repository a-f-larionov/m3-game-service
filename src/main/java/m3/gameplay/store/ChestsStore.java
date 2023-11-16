package m3.gameplay.store;

import m3.gameplay.dto.ChestDto;
import m3.gameplay.dto.PrizeDto;

import java.util.List;

import static m3.gameplay.enums.DataObjects.*;

public class ChestsStore {

    public static List<ChestDto> chests = List.of(

            /** Map-001 */
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3L),
                    new PrizeDto(STUFF_LIGHTNING, 1L),
                    new PrizeDto(STUFF_SHUFFLE, 1L)
            )),
            new ChestDto(2L, List.of(
                    new PrizeDto(STUFF_GOLD, 500L),
                    new PrizeDto(STUFF_HUMMER, 2L)
            )),
            new ChestDto(3L, List.of(
                    new PrizeDto(STUFF_GOLD, 500L),
                    new PrizeDto(STUFF_LIGHTNING, 3L)
            )),

            /** Map-002 */
            new ChestDto(4L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3L),
                    new PrizeDto(STUFF_LIGHTNING, 2L),
                    new PrizeDto(STUFF_SHUFFLE, 1L)
            )),
            new ChestDto(5L, List.of(
                    new PrizeDto(STUFF_GOLD, 500L),
                    new PrizeDto(STUFF_HUMMER, 3L)
            )),
            new ChestDto(6L, List.of(
                    new PrizeDto(STUFF_GOLD, 500L),
                    new PrizeDto(STUFF_LIGHTNING, 3L),
                    new PrizeDto(STUFF_SHUFFLE, 1L)
            )),

            /** Map-003 */
            new ChestDto(7L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3L),
                    new PrizeDto(STUFF_LIGHTNING, 2L),
                    new PrizeDto(STUFF_SHUFFLE, 1L)
            )),
            new ChestDto(8L, List.of(
                    new PrizeDto(STUFF_GOLD, 1500L),
                    new PrizeDto(STUFF_SHUFFLE, 2L)
            )),
            new ChestDto(9L, List.of(
                    new PrizeDto(STUFF_GOLD, 1500L),
                    new PrizeDto(STUFF_HUMMER, 3L)
            ))
    );
}
