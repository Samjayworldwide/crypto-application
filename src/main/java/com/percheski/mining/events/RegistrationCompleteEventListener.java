package com.percheski.mining.events;

import com.percheski.mining.entities.enums.Roles;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.EmailNotSentException;
import com.percheski.mining.security.JWTGenerator;
import com.percheski.mining.services.EmailServices;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Log4j2
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final JWTGenerator jwtGenerator;
    private final EmailServices emailServices;
    private UserEntity theUser;

    @Value("${frontend.url}")
    private String frontEndUrl;

    @Value("${email}")
    private String email;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        String verificationToken = jwtGenerator.generateSignupToken(theUser.getEmail(), 5L);
        String url = frontEndUrl+ "/registration/email_verification?token=" + verificationToken;
        String adminUrl = frontEndUrl+ "/registration/admin/email_verification?token=" + verificationToken;

        new Thread(() -> {
            try {
                if (theUser.getRoles().equals(Roles.USER)){
                sendVerificationEmail(url, theUser.getEmail());
                }
                else{
                    sendVerificationEmail(adminUrl,email);
                }
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException("Email not sent.");
            }
        }).start();

        log.info("Click the link to verify your registration :  {}", url);
    }

    private void sendVerificationEmail(String url, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Percheski";
        String mailContent = "<p> Hi, " + theUser.getFirstName() + ", </p>" +
                "<p>Thank you for registering with us." + "\n" +
                "Please, follow the link below to complete your registration. \nThis link <strong> expires in 5 minute</strong>.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> The Fundz Portal Service";

        emailServices.sendSimpleMessage(email, subject, mailContent, senderName);
    }
}
