package com.example.back.exception;

public class LoginIsMissingException extends RuntimeException {
    public LoginIsMissingException(String message){
        super(message);
    }
}
