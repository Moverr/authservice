package com.kodeinc.authservice.models.dtos.exceptions;

public class CustomForbiddenRequestException extends RuntimeException{
    public CustomForbiddenRequestException(){
        super();
    }

    public CustomForbiddenRequestException(String msg){
        super(msg);
    }
}
