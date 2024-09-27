package com.percheski.mining.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.percheski.mining.entities.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {
    @Size(min = 3, max = 15, message = "First name is too long or short")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Size(min = 3, max = 15, message = "Last name is too long or short")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Size(max = 35)
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email should not be blank!")
    private String email;

    @NotBlank(message = "Date of birth can not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dob;

    @Size(min = 4, max = 15, message = "Password too short or long")
    @NotBlank(message = "Password cannot be blank!")
    private String password;

    @Size(min = 4, max = 15, message = "Password too short or long")
    @NotBlank(message = "Password cannot be blank!")
    private String confirmPassword;

    @Size(min = 3, max = 100, message = "Address is too long or short")
    @NotBlank(message = "Address cannot be empty!")
    private  String address;

    private String phoneNumber;

    private String gender;

}
