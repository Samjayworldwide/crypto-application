package com.percheski.mining.services.implementations;

import com.percheski.mining.entities.enums.Currency;
import com.percheski.mining.entities.enums.Networks;
import com.percheski.mining.entities.enums.Status;
import com.percheski.mining.entities.model.PaymentData;
import com.percheski.mining.entities.model.UserEntity;
import com.percheski.mining.exceptions.EmailNotSentException;
import com.percheski.mining.exceptions.FileException;
import com.percheski.mining.exceptions.UnauthorizedException;
import com.percheski.mining.exceptions.UserNotFoundException;
import com.percheski.mining.payload.request.PaymentRequest;
import com.percheski.mining.repositories.PaymentRepository;
import com.percheski.mining.repositories.UserRepository;
import com.percheski.mining.services.EmailServices;
import com.percheski.mining.services.PaymentServices;
import com.percheski.mining.utils.FileUpload;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class PaymentImpl implements PaymentServices {
    private final UserRepository userRepository;
    private final EmailServices emailServices;
    private final PaymentRepository paymentRepository;
    private final FileUpload fileUpload;


    @Override
    public String confirmPayment(PaymentRequest request) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (user.getNumberOfTimes() > 4) {
            throw new UnauthorizedException("Maximum number of stake reached. Do not make another payment");
        }

        if (request.getImageDataUrl().isEmpty()) {
            throw new FileException("File is empty");
        }

        if (paymentRepository.existsByName(request.getImageDataUrl().getOriginalFilename())) {
            throw new FileException("File already Exist");
        }

        PaymentData paymentData = PaymentData.builder()
                .name(request.getImageDataUrl().getOriginalFilename())
                .type(request.getImageDataUrl().getContentType())
                .imageDataUrl((request.getImageDataUrl() == null) ? null : fileUpload.uploadCoverPhoto(request.getImageDataUrl()))
                .currency(Currency.valueOf(request.getCurrency().toUpperCase()))
                .networks(Networks.valueOf(request.getNetworks().toUpperCase()))
                .status(Status.PENDING)
                .build();

        paymentRepository.save(paymentData);

        user.addImage(paymentData);

        userRepository.save(user);

        String emailTo = "emmanuelelochukwu33@gmail.com";
        String subject = "Payment Activated";
        String message = "The user " + email + " made a payment to " + "'s network with the payment id of " + paymentData.getId();
//            + request.getCoin() +" on "+request.getNetwork()
        new Thread(() -> {
            try {
                emailServices.sendSimpleMessage(emailTo, subject, message, "Help");
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException("Email not sent.");
            }
        }).start();

        return "Awaiting confirmation";
    }


}
