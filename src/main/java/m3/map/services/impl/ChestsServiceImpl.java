package m3.map.services.impl;

import lombok.RequiredArgsConstructor;
import m3.map.dto.ChestDto;
import m3.map.services.ChestsService;
import m3.map.store.ChestsStore;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChestsServiceImpl implements ChestsService {
    @Override
    public ChestDto getById(Long chestId) {
        return ChestsStore.chests.stream()
                .filter(chest -> chest.getId().equals(chestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Chest not found"));
    }
}
