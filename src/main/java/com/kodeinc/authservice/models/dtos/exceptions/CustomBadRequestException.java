package com.kodeinc.authservice.models.dtos.exceptions;

public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(){
        super();
    }

    public CustomBadRequestException(String msg){
        super(msg);
    }
}
