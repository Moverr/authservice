package com.kodeinc.authservice.exceptions;

public class CustomUnAuthorizedException extends RuntimeException{
    public CustomUnAuthorizedException(){
        super();
    }

    public CustomUnAuthorizedException(String msg){
        super(msg);
    }
}
