package m3.game.services;

import m3.game.dto.ChestDto;

public interface ChestsService {

    ChestDto getById(Long chestId);
}
