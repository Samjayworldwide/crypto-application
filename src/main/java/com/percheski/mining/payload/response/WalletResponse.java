package com.percheski.mining.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WalletResponse {
    private String amount;
    private String walletAddress;
    private String coin;
    private String network;
}
