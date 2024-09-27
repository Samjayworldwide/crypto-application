package com.percheski.mining.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String dateTime;
    private String name;
    private Long numberOfTimes;

    public LoginResponse(String accessToken, String refreshToken,String name,Long numberOfTimes) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.dateTime = String.valueOf(LocalDateTime.now());
        this.name = name;
        this.numberOfTimes =numberOfTimes;
    }

}
