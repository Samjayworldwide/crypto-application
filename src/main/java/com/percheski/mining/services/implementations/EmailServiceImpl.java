package com.percheski.mining.services.implementations;


import com.percheski.mining.services.EmailServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
public class EmailServiceImpl implements EmailServices {

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleMessage(String email, String subject, String message, String senderName)
            throws MessagingException,
            UnsupportedEncodingException {
        MimeMessage messageFormat = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(messageFormat);
        messageHelper.setFrom("mbanisisamuel@yahoo.com", senderName);
        messageHelper.setTo(email);
        messageHelper.setSubject(subject);
        messageHelper.setText(message, true);
        mailSender.send(messageFormat);
    }
}
