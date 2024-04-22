package m3.game.services.impl;

import lombok.RequiredArgsConstructor;
import m3.game.dto.rs.GotStuffRsDto;
import m3.game.mappers.StuffMapper;
import m3.game.services.StuffService;
import m3.lib.entities.UserStuffEntity;
import m3.lib.repositories.UserStuffRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StuffServiceImpl implements StuffService {

    private final UserStuffRepository userStuffRepository;
    private final StuffMapper stuffMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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

    @Override
    public void sendStuffToUser(Long userId) {
        var stuff = getUserStuff(userId);
        GotStuffRsDto stuffRsDto = stuffMapper.entityToDto(stuff);
        //@todo got it as a value from application.yaml
        kafkaTemplate.send("topic-client", stuffRsDto);
    }

    @Override
    public UserStuffEntity getUserStuff(Long userId) {
        System.out.println(userId);
        System.out.println(userStuffRepository.findById(userId));
        //@TODO? GetOrThrow?
        return userStuffRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User stuff not found"));
    }
}