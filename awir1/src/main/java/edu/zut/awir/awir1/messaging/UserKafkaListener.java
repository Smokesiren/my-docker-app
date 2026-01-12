package edu.zut.awir.awir1.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class UserKafkaListener {
    private static final Logger log =
            LoggerFactory.getLogger(UserKafkaListener.class);
    private final ObjectMapper objectMapper;
    @KafkaListener(
            topics = "${app.kafka.user-events-topic:awir.users.events}",
            groupId = "${spring.kafka.consumer.group-id:awir-users-group}"
    )
    public void onMessage(String payload) {
        try {
            UserEvent event = objectMapper.readValue(payload, UserEvent.class);
            log.info("Odebrano Kafka event: {}", event);
        } catch (Exception e) {
            log.error("Nie udało się zdeserializować Kafka event: {}", payload, e);
        }
    }
}