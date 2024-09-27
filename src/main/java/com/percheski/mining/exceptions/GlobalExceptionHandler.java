package com.percheski.mining.exceptions;
import com.percheski.mining.entities.model.ErrorDetail;
import com.percheski.mining.payload.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUserAlreadyExistException(final UserAlreadyExistException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("The user already exist")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMisMatchException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handlePasswordMisMatchException(final PasswordMisMatchException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("The password does not match")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUserNotFoundException(final UserNotFoundException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .debugMessage("User not Found")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleUnauthorizedException(final UnauthorizedException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .debugMessage("User not Authorized")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleInsufficientFundException(final InsufficientFundException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Insufficient fund")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleEmailNotVerifiedException(final EmailNotVerifiedException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .debugMessage("Email not verified")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MiningOnProgressException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleMiningOnProgressException(final MiningOnProgressException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("On going mining")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleEmailNotSentException(final EmailNotSentException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Email not sent")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( NewAndOldPasswordException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleNewAndOldPasswordException(final  NewAndOldPasswordException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("New and old passwords should not match")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( TokenExpirationException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleTokenExpirationException(final  TokenExpirationException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Token has expired")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( FileException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleFileException(final FileException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .debugMessage("File Error")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //@ExceptionHandler( FileException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleGlobalException(final MethodArgumentNotValidException exception){
        ErrorDetail errorDetail = ErrorDetail.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .debugMessage("File Error")
                .dateTime(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse<ErrorDetail> response = new ApiResponse<>(exception.getMessage(), errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
