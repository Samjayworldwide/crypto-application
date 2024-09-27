package com.percheski.mining.controllers;

import com.percheski.mining.payload.request.AdminChangeCoinAmount;
import com.percheski.mining.payload.request.AdminChangeWalletRequest;
import com.percheski.mining.payload.request.UserLoginRequest;
import com.percheski.mining.payload.request.UserSignupRequest;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.payload.response.LoginResponse;
import com.percheski.mining.payload.response.PaymentDataResponse;
import com.percheski.mining.payload.response.UserResponse;
import com.percheski.mining.services.AdminServices;
import com.percheski.mining.services.SignupAndLoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminServices adminServices;
    private final SignupAndLoginUser signupAndLoginUser;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody UserLoginRequest request,
                                                            HttpServletRequest httpReq){
        return ResponseEntity.ok(new ApiResponse<>("Login successfully",signupAndLoginUser.login(request,httpReq)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> createAdmin(@Valid @RequestBody UserSignupRequest request, HttpServletRequest httpReq){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(signupAndLoginUser.register(request, httpReq)));
    }

    @GetMapping("/fetchUser/{email}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable String email){
      return   ResponseEntity.ok().body(new ApiResponse<>("Successful",adminServices.getUser(email)));
    }

    @GetMapping("/search_id/{id}")
    public ResponseEntity<ApiResponse<PaymentDataResponse>>  searchPaymentId(@PathVariable Long id){
        return ResponseEntity.ok().body(new ApiResponse<>("Search Successful",adminServices.searchPaymentId(id)));
    }

    @PostMapping("/approvePayment")
    public ResponseEntity<ApiResponse<String>> verifyPayment(@RequestParam ("id") Long id,
                                                             @RequestParam("status") String status){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(adminServices.verifyPayment(id,status)));
    }

    @GetMapping("/viewProof/{filename}")
    public ResponseEntity<ApiResponse<String>> viewPictureProof(@PathVariable String filename){
        return ResponseEntity.ok().body(new ApiResponse<>("Successful", adminServices.viewPictureProof(filename)));
    }

    @PostMapping("/changeWallet")
    public ResponseEntity<ApiResponse<String>> changeWalletDetails( @RequestBody AdminChangeWalletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(adminServices.changeWalletDetails(request)));
    }

    @PostMapping("/change_amount")
    public ResponseEntity<ApiResponse<String>> changeCoinAmount(@RequestBody AdminChangeCoinAmount coin){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(adminServices.changeCoinAmount(coin)));
    }


}
