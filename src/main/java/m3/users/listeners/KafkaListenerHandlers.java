package m3.users.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m3.users.dto.rq.LogRqDto;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "topic-map-and-points", groupId = "2")
public class KafkaListenerHandlers {

    @KafkaHandler
    public void log(LogRqDto rq) {
        log.info(rq.toString());
        log.info(rq.getMessage());
    }

}