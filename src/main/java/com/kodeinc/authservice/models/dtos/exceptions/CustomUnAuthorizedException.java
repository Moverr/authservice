package com.kodeinc.authservice.models.dtos.exceptions;

public class CustomUnAuthorizedException extends RuntimeException{
    public CustomUnAuthorizedException(){
        super();
    }

    public CustomUnAuthorizedException(String msg){
        super(msg);
    }
}
