package m3.map.services;

import m3.map.dto.ChestDto;

public interface ChestsService {

    ChestDto getById(Long chestId);
}
