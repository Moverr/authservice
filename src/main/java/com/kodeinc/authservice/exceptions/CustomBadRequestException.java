package com.kodeinc.authservice.exceptions;

public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(){
        super();
    }

    public CustomBadRequestException(String msg){
        super(msg);
    }
}
