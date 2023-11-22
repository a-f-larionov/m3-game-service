package m3.gameplay.services.impl;

import lombok.RequiredArgsConstructor;
import m3.gameplay.services.StuffService;
import m3.lib.repositories.UserStuffRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StuffServiceImpl implements StuffService {

    private final UserStuffRepository userStuffRepository;

    @Override
    public void giveAGold(Long userId, Long qty) {
        userStuffRepository.incrementGoldQty(userId, qty);
    }

    @Override
    public void giveAHummer(Long userId, Long qty) {
        userStuffRepository.incrementHummerQty(userId, qty);
    }

    @Override
    public void giveALightning(Long userId, Long qty) {
        userStuffRepository.incrementLightningQty(userId, qty);
    }

    @Override
    public void giveAShuffle(Long userId, Long qty) {
        userStuffRepository.incrementShuffleQty(userId, qty);
    }

    @Override
    public void spendAHummer(Long userId, Long qty) {
        userStuffRepository.decrementHummerQty(userId, qty);
    }

    @Override
    public void spendALightning(Long userId, Long qty) {
        userStuffRepository.decrementLightningQty(userId, qty);
    }

    @Override
    public void spendAShuffle(Long userId, Long qty) {
        userStuffRepository.decrementShuffleQty(userId, qty);
    }

    @Override
    public void spendAGold(Long userId, Long qty) {
        userStuffRepository.decrementGoldQty(userId, qty);
    }
}
