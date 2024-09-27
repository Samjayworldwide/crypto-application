package com.percheski.mining.services.implementations;


import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.EmailNotSentException;
import com.percheski.mining.exceptions.TokenExpirationException;
import com.percheski.mining.exceptions.UserNotFoundException;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.security.JWTGenerator;
import com.percheski.mining.services.EmailServices;
import com.percheski.mining.services.VerificationServices;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;


@RequiredArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationServices {
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepo;
    private final EmailServices emailServices;

    @Override
    public String verifyUserEmail(String token) {
        if (!jwtGenerator.validateToken(token)){
            throw new TokenExpirationException("Token has expired");
        }

        String email = jwtGenerator.getEmailFromJWT(token);
        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        user.setVerified(true);

        userRepo.save(user);

        return "Congratulation " +user.getFirstName()+"," +
                " You are verified";
    }

    @Override
    public void re_sendVerificationEmail(String email) {

        String verificationToken = jwtGenerator.generateSignupToken(email, 5L);
        String url = "http://localhost:8080/registration/email_verification?token="+verificationToken;

        String subject = "Email Verification";
        String senderName = "Fundz";
        String mailContent = "<p>Please, follow the link below to complete your registration. \nThis link <strong> expires in 5 minute</strong>.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> The Fundz Portal Service";
        new Thread(() -> {
            try {
                emailServices.sendSimpleMessage(email, subject, mailContent, senderName);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException("Email not sent.");
            }
        }).start();
    }

}
