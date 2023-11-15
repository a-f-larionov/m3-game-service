package m3.map.services.impl;

import m3.lib.repositories.UserStuffRepository;
import m3.map.services.StuffService;
import org.springframework.stereotype.Service;

@Service
public class StuffServiceImpl implements StuffService {

    private final UserStuffRepository userStuffRepository;

    public StuffServiceImpl(UserStuffRepository userStuffRepository) {
        this.userStuffRepository = userStuffRepository;
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
    public void giveAGold(Long userId, Long qty) {
        userStuffRepository.incrementGoldQty(userId, qty);
    }
}
