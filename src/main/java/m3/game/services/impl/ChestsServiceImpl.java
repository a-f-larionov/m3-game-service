package m3.game.services.impl;

import lombok.RequiredArgsConstructor;
import m3.game.dto.ChestDto;
import m3.game.services.ChestsService;
import m3.game.store.ChestsStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ChestsServiceImpl implements ChestsService {
    @Override
    public ChestDto getById(Long chestId) {
        //@todo-a getById! what is a pattern?
        return ChestsStore.chests.stream()
                .filter(chest -> chest.getId().equals(chestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Chest not found"));
    }
}
