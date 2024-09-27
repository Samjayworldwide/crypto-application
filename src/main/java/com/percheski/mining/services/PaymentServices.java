package com.percheski.mining.services;

import com.percheski.mining.payload.request.PaymentRequest;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface PaymentServices {
    String confirmPayment( PaymentRequest request) throws IOException, MessagingException;

}
