package com.percheski.mining.payload.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentDataResponse {
    private Long id;
    private String name;
    private String currency;

    private String networks;


    private String status;

    private String imageDataUrl;
}
