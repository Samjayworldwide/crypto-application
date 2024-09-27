package com.percheski.mining.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {
    private Long id;
    private Double amount;
    private String status;
    private String currency;
}
