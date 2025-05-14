package com.example.back.exception;

public class RequestArgumentException extends RuntimeException{
    public RequestArgumentException(String message){
        super(message);
    }
}
