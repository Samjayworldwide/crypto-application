package com.percheski.mining.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class WalletRequest {

    @NotBlank(message = "currency name cannot be blank")
    @NotEmpty(message = "currency name cannot be Empty")
    private String currency;


    @NotBlank(message = "network name cannot be blank")
    @NotEmpty(message = "network name cannot be Empty")
    private String networks;
}
