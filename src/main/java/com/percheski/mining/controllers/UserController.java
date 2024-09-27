package com.percheski.mining.controllers;

import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.payload.response.ProfileResponse;
import com.percheski.mining.payload.response.UserResponse;
import com.percheski.mining.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserServices userServices;

    @GetMapping("/fetchUser")
    public ResponseEntity<ApiResponse<ProfileResponse>> getDetails(){
        return   ResponseEntity.ok().body(new ApiResponse<>("Successful",userServices.getDetails()));
    }

}
