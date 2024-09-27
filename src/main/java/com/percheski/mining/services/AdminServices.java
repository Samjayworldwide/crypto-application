package com.percheski.mining.services;

import com.percheski.mining.payload.request.AdminChangeCoinAmount;
import com.percheski.mining.payload.request.AdminChangeWalletRequest;
import com.percheski.mining.payload.request.UserSignupRequest;

import com.percheski.mining.payload.response.PaymentDataResponse;
import com.percheski.mining.payload.response.UserResponse;


public interface AdminServices {

    UserResponse getUser(String email);
    String verifyPayment(Long id,String status);

//    String createAdmin(UserSignupRequest request);

    String viewPictureProof(String filename);
    String changeWalletDetails(AdminChangeWalletRequest request);
    String changeCoinAmount(AdminChangeCoinAmount coin);
    PaymentDataResponse searchPaymentId(Long id);

}
