package com.percheski.mining.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PaymentRequest {

    @NotBlank(message = "currency name cannot be blank")
    @NotEmpty(message = "currency name cannot be Empty")
    private String currency;


    @NotBlank(message = "network name cannot be blank")
    @NotEmpty(message = "network name cannot be Empty")
    private String networks;


    @NotNull(message = "proof of payment cannot be null")
    private MultipartFile imageDataUrl;
}
