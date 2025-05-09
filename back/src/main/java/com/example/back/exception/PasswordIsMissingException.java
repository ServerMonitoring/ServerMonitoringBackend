package com.example.back.exception;

public class PasswordIsMissingException extends RuntimeException{
    public PasswordIsMissingException(String message){
        super(message);
    }
}
