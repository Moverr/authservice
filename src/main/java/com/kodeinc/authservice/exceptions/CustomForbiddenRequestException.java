package com.kodeinc.authservice.exceptions;

public class CustomForbiddenRequestException extends RuntimeException{
    public CustomForbiddenRequestException(){
        super();
    }

    public CustomForbiddenRequestException(String msg){
        super(msg);
    }
}
