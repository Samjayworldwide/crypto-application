package com.percheski.mining.payload.response;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class ApiResponse<T> {
    private String responseMessage;
    private T responseData;
    private final String responseTime = String.valueOf(LocalDateTime.now());
    private HttpHeaders headers;

    public ApiResponse(String message) {
        this.responseMessage = message;

    }
    public ApiResponse(String message, T data) {
        this.responseMessage = message;
        this.responseData = data;
    }

    public ApiResponse(String responseMessage, T responseData, HttpHeaders headers) {
        this.responseMessage = responseMessage;
        this.responseData = responseData;
        this.headers = headers;
    }
}
