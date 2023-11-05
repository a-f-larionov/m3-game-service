package m3.map.services.impl;

import lombok.RequiredArgsConstructor;
import m3.map.data_store.ChestsDataStore;
import m3.map.dto.ChestDto;
import m3.map.services.ChestsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChestsServiceImpl implements ChestsService {
    @Override
    public ChestDto getById(Long chestId) {
        return ChestsDataStore.chests.stream()
                .filter(c -> c.getId().equals(chestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Chest not found"));
    }
}
