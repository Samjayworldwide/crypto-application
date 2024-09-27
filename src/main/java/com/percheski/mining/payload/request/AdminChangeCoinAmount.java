package com.percheski.mining.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminChangeCoinAmount {
    private String coin;
    private String amount;
}
