package com.percheski.mining.services;

import com.percheski.mining.payload.request.WalletRequest;
import com.percheski.mining.payload.response.WalletResponse;


public interface WalletServices {
    WalletResponse viewCoinWallet(WalletRequest request);
}
