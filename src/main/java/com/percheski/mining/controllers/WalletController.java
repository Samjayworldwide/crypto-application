package com.percheski.mining.controllers;

import com.percheski.mining.payload.request.WalletRequest;
import com.percheski.mining.payload.response.ApiResponse;
import com.percheski.mining.payload.response.WalletResponse;
import com.percheski.mining.services.WalletServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/wallet")
public class WalletController {
    private final WalletServices walletServices;

    @PostMapping("/viewAddress")
    public ResponseEntity<ApiResponse<WalletResponse>> viewCoinWallet(@RequestBody WalletRequest walletRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","image/png");
        return ResponseEntity.ok(new ApiResponse<>("viewed Successfully",walletServices.viewCoinWallet(walletRequest),headers));
    }
}
