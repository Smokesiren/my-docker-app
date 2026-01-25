package edu.zut.awir.awir1.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.zut.awir.awir1.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJmsProducer {
    private static final Logger log = LoggerFactory.getLogger(UserJmsProducer.class);
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.jms.user-events-queue:awir.user.events}")
    private String queue;

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
            jmsTemplate.convertAndSend(queue, payload);
            log.info("Wysłano JMS na {}: {}", queue, payload);
        } catch (Exception e) {
            log.error("CRITICAL JMS ERROR: {}", e.getMessage());
        }
    }
}