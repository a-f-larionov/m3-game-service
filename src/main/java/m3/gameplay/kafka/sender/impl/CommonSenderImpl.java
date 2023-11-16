package m3.gameplay.kafka.sender.impl;

import lombok.RequiredArgsConstructor;
import m3.lib.dto.rq.StatisticRqDto;
import m3.lib.enums.StatisticEnum;
import m3.gameplay.kafka.sender.CommonSender;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonSenderImpl implements CommonSender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void statistic(Long userId, StatisticEnum statisticEnum, String paramA, String paramB) {
        kafkaTemplate.send("topic-common", StatisticRqDto.builder()
                .userId(userId)
                .statId(statisticEnum)
                .paramA(paramA)
                .paramB(paramB)
                .build());
    }
}
