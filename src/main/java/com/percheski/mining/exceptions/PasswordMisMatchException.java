package com.percheski.mining.exceptions;

public class PasswordMisMatchException extends RuntimeException{
    public PasswordMisMatchException(String message) {
        super(message);
    }
}
