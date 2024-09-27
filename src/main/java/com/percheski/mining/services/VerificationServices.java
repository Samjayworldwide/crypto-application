package com.percheski.mining.services;


public interface VerificationServices {
    String verifyUserEmail(String token);
    void re_sendVerificationEmail(String email);

}
