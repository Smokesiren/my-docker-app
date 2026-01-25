package edu.zut.awir.awir1.service;
import edu.zut.awir.awir1.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    @Value("${app.mail.admin:ca51660@zut.edu.pl}")
    private String adminAddress;
    public void sendUserCreated(User user) {
        String subject = "Nowy użytkownik zarejestrowany";
        String text = "Utworzono nowego użytkownika:\n" +
                "ID: " + user.getId() + "\n" +
                "Imię i nazwisko: " + user.getName() + "\n" +
                "Email: " + user.getEmail();
        send(adminAddress, subject, text);
// kopia do samego użytkownika
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            send(user.getEmail(), "Witaj w systemie", "Twoje konto zostało utworzone.");
        }
    }
    public void sendUserUpdated(User user) {
        String subject = "Dane użytkownika zmodyfikowane";
        String text = "Zmodyfikowano dane użytkownika:\n" +
                "ID: " + user.getId() + "\n" +
                "Imię i nazwisko: " + user.getName() + "\n" +
                "Email: " + user.getEmail();
        send(adminAddress, subject, text);
    }
    private void send(String to, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
            log.info("Wysłano e-mail do {}: {}", to, subject);
        } catch (Exception e) {
            log.error("Błąd wysyłania e-maila do {}: {}", to, e.getMessage(), e);
        }
    }
}
