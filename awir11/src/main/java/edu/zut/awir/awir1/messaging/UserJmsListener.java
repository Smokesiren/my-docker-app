package edu.zut.awir.awir1.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class UserJmsListener {
    private static final Logger log =
            LoggerFactory.getLogger(UserJmsListener.class);
    private final ObjectMapper objectMapper;
    @JmsListener(destination = "${app.jms.user-events-queue:awir.user.events}")
    public void onMessage(String payload) {
        try {
            UserEvent event = objectMapper.readValue(payload, UserEvent.class);
            log.info("Odebrano JMS event: {}", event);
// Na +: zapisz do tabeli user_events albo wyślij mail
        } catch (Exception e) {
            log.error("Nie udało się zdeserializować JMS event: {}", payload, e);
        }
    }
}
