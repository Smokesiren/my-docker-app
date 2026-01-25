package edu.zut.awir.awir1.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zut.awir.awir1.model.User; // Upewnij się, że ścieżka do User jest poprawna
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(UserKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.user-events-topic:awir.users.events}")
    private String topic;

    public void sendUserCreated(User user) {
        send(user, "USER_CREATED");
    }

    public void sendUserUpdated(User user) {
        send(user, "USER_UPDATED");
    }

    private void send(User user, String type) {
        UserEvent event = new UserEvent(user.getId(), user.getName(), user.getEmail(), type);
        try {
            String payload = objectMapper.writeValueAsString(event);
            // Klucz wiadomości (ID użytkownika) zapewnia kolejność zdarzeń dla tego samego usera
            String key = (user.getId() == null) ? null : user.getId().toString();
            kafkaTemplate.send(topic, key, payload);
            log.info("Wysłano Kafka event na {}: {}", topic, payload);
        } catch (JsonProcessingException e) {
            log.error("Błąd serializacji Kafka event: {}", e.getMessage(), e);
        }
    }
}