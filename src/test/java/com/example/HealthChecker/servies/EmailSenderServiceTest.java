package com.example.HealthChecker.servies;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderService emailSenderService;

    @Test
    void testSendEmail() {
        String toEmail = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        emailSenderService.sendEmail(toEmail, subject, body);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals("izal.mert@gmail.com", sentMessage.getFrom());
        assertEquals(toEmail, Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals(subject, sentMessage.getSubject());
        assertEquals(body, sentMessage.getText());
    }
}

