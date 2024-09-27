package com.percheski.mining.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email should not be blank!")
    private String email;

    @NotBlank(message = "Password cannot be blank!")
    private String password;
}
