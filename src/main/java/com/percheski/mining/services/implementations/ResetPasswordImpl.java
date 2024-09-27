package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.model.OTPEntity;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.*;
import com.percheski.mining.payload.request.ResetPasswordRequest;
import com.percheski.mining.repositories.OTPRepository;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.services.GenerateOTPServices;
import com.percheski.mining.services.ResetPassword;
import com.percheski.mining.services.EmailServices;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetPasswordImpl implements ResetPassword {
    private final UserRepository repository;
    private final EmailServices emailServices;
    private final PasswordEncoder encoder;
    private final GenerateOTPServices generateOTPServices;
    private final OTPRepository otpRepository;



    @Transactional
    @Override
    public String sendOtpToEmail(String email) {
       Optional<UserEntity> user = repository.findUserEntityByEmail(email.toLowerCase());
       if(user.isPresent()){
           int otp =  generateOTPServices.generateOTP();

           OTPEntity otpEntity = OTPEntity.builder()
                   .otp(otp)
                   .expirationDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                   .email(email)
                   .build();


           String subject = "Reset Password";
           String senderName = "Fundz";
           String mailContent = "<p>Please, enter the number below to reset your password. \nThis number <strong> expires in 10 minute</strong>.</p>"+
                   otp +
                   "<p> Thank you <br> The Fundz Portal Service";
           new Thread(() -> {
               try {
                   emailServices.sendSimpleMessage(email, subject, mailContent, senderName);
               } catch (MessagingException | UnsupportedEncodingException e) {
                   throw new EmailNotSentException("Email not sent.");
               }
           }).start();

          OTPEntity otp1 = otpRepository.findByEmail(email.toLowerCase());
           if( otp1 != null){
               otp1.setOtp(otp);
               otp1.setCreatedAt(new Date(System.currentTimeMillis()));
               otp1.setExpirationDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
               otpRepository.save(otp1);
               return "Check email for OTP";
           }else {

               otpRepository.save(otpEntity);

               return "Check email for OTP";
           }
       }
       return "Account does not exist";
    }

    @Override
    public String resetPassword(String email, ResetPasswordRequest request) {
        UserEntity user = repository.findUserEntityByEmail(email.toLowerCase())
                .orElseThrow(()-> new UserNotFoundException("User not found."));

        if (!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new PasswordMisMatchException("Password mismatched.");
        }

        if(encoder.matches(request.getNewPassword(), user.getPassword())){
            throw new NewAndOldPasswordException("The new and old password should not be same.");
        }

        user.setPassword(encoder.encode(request.getNewPassword()));

        repository.save(user);
        return "Password reset successfully!";
    }


    @Transactional
    public String verifyOTP(int otp, String email){
        OTPEntity otpEntity = otpRepository.findByOtpAndEmail(otp,email.toLowerCase())
                .orElseThrow(() -> new UserNotFoundException("Invalid OTP"));
        if(otpEntity.getExpirationDate().before(Date.from(Instant.now()))){
            otpRepository.deleteById(otpEntity.getId());
            return "OTP has expired";
        }
        otpRepository.deleteById(otpEntity.getId());
        return "successful";
    }

}
