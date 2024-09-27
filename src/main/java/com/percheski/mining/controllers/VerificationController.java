package com.percheski.mining.controllers;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.services.VerificationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
public class VerificationController {
    private final VerificationServices verificationServices;

    @GetMapping("/email_verification")
    public ResponseEntity<ApiResponse<String>> verifyUserEmail(@RequestParam String token){

       return ResponseEntity.ok(new ApiResponse<>("Email Verified, Log in into your account", verificationServices.verifyUserEmail(token)));
    }

    @GetMapping("/admin/email_verification")
    public ResponseEntity<ApiResponse<String>> verifyAdminUserEmail(@RequestParam String token){

        return ResponseEntity.ok(new ApiResponse<>("Email Verified, Log in into your account", verificationServices.verifyUserEmail(token)));
    }


    @PostMapping("/re_verification")
    public ResponseEntity<ApiResponse<String>> reVerificationAdminUserEmail(@RequestParam String email){
        verificationServices.re_sendVerificationEmail(email);
        return ResponseEntity.ok(new ApiResponse<>("Verification email has been sent to your account"));
    }

}
