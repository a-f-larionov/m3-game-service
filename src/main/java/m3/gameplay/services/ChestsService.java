package m3.gameplay.services;

import m3.gameplay.dto.ChestDto;

public interface ChestsService {

    ChestDto getById(Long chestId);
}
