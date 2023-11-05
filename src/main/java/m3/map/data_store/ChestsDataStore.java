package m3.map.data_store;

import m3.map.dto.ChestDto;
import m3.map.dto.PrizeDto;

import java.util.List;

import static m3.map.enums.DataObjects.*;

public class ChestsDataStore {

    public static List<ChestDto> chests = List.of(

            /** Map-001 */
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3),
                    new PrizeDto(STUFF_LIGHTNING, 1),
                    new PrizeDto(STUFF_SHUFFLE, 1)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 500),
                    new PrizeDto(STUFF_HUMMER, 2)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 500),
                    new PrizeDto(STUFF_LIGHTNING, 3)
            )),

            /** Map-002 */
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3),
                    new PrizeDto(STUFF_LIGHTNING, 2),
                    new PrizeDto(STUFF_SHUFFLE, 1)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 500),
                    new PrizeDto(STUFF_HUMMER, 3)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 500),
                    new PrizeDto(STUFF_LIGHTNING, 3),
                    new PrizeDto(STUFF_SHUFFLE, 1)
            )),

            /** Map-003 */
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_HUMMER, 3),
                    new PrizeDto(STUFF_LIGHTNING, 2),
                    new PrizeDto(STUFF_SHUFFLE, 1)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 1500),
                    new PrizeDto(STUFF_SHUFFLE, 2)
            )),
            new ChestDto(1L, List.of(
                    new PrizeDto(STUFF_GOLD, 1500),
                    new PrizeDto(STUFF_HUMMER, 3)
            ))
    );
}
