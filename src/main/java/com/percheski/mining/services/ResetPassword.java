package com.percheski.mining.services;

import com.percheski.mining.payload.request.ResetPasswordRequest;

public interface ResetPassword {
    String sendOtpToEmail(String email);
    String resetPassword(String email, ResetPasswordRequest request);

    String verifyOTP(int otp, String email);
}
