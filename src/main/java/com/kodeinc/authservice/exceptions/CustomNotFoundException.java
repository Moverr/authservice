package com.kodeinc.authservice.exceptions;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(){
        super();
    }

    public CustomNotFoundException(String msg){
        super(msg);
    }
}
