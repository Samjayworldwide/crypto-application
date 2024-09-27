package com.percheski.mining.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AdminChangeWalletRequest {
   private String coin;
   private String network;
    private String walletAddress;
}
