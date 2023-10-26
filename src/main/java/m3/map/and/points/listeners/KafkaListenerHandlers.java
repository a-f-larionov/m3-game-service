package m3.map.and.points.listeners;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
@KafkaListener(topics = "topic-map-and-points", groupId = "2")
public class KafkaListenerHandlers {

}