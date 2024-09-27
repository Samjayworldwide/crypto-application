package com.percheski.mining.controllers;

import com.percheski.mining.payload.request.UserLoginRequest;
import com.percheski.mining.payload.request.UserSignupRequest;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.payload.response.LoginResponse;
import com.percheski.mining.services.SignupAndLoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class LoginAndRegisterController {
   private final SignupAndLoginUser signupAndLoginUser;

   @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registration(@Valid @RequestBody UserSignupRequest request,
                                                            HttpServletRequest requests){
       return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(signupAndLoginUser.register(request,requests)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody UserLoginRequest request,
                                                            HttpServletRequest httpReq){
       return ResponseEntity.ok(new ApiResponse<>("Login successfully",signupAndLoginUser.login(request,httpReq)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(){
        SecurityContextHolder.clearContext();
       return ResponseEntity.ok(new ApiResponse<>("logged out successfully"));
    }
}

