package com.percheski.mining.services;


import com.percheski.mining.payload.response.LoginResponse;
import com.percheski.mining.payload.request.UserLoginRequest;
import com.percheski.mining.payload.request.UserSignupRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface SignupAndLoginUser {
    String register(UserSignupRequest request,HttpServletRequest requests);
    LoginResponse login(UserLoginRequest request, HttpServletRequest httpReq);
    void logout();
}
