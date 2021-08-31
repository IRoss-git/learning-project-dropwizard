package com.ilya.service.exception;

public class BasicExceptionResponse {

    public Integer code;

    public String message;

    public BasicExceptionResponse() {}

    public BasicExceptionResponse(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
