package com.kodeinc.authservice.models.dtos.exceptions;

public class CustomNotFoundException extends RuntimeException{
    public CustomNotFoundException(){
        super();
    }

    public CustomNotFoundException(String msg){
        super(msg);
    }
}
