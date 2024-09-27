package com.percheski.mining.controllers;

import com.percheski.mining.payload.request.ResetPasswordRequest;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.services.ResetPassword;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reset_password")
public class ResetPasswordController {
    private final ResetPassword resetPassword;

    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> sendOtpToEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(new ApiResponse<>(resetPassword.sendOtpToEmail(email)));
    }


    @PostMapping("/verify/{otp}/{email}")
    public ResponseEntity<ApiResponse<String>> VerifyOTp(@PathVariable int otp, @PathVariable String email){
        return ResponseEntity.ok(new ApiResponse<>(resetPassword.verifyOTP(otp, email)));
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ApiResponse<String>>resetPassword(@RequestParam("email") String email,
                                                           @Valid @RequestBody ResetPasswordRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(resetPassword.resetPassword(email,request)));
    }


}
