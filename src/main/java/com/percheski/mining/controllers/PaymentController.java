package com.percheski.mining.controllers;

import com.percheski.mining.payload.request.PaymentRequest;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.services.PaymentServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deposit")
public class PaymentController {

    private final PaymentServices paymentServices;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> confirmPayment(@Valid PaymentRequest request)
            throws MessagingException, IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(paymentServices.confirmPayment(request)));
    }
}
