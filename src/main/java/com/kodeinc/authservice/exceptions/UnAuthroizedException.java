package com.kodeinc.authservice.exceptions;

import org.springframework.http.HttpStatus;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */
public class UnAuthroizedException extends RuntimeException{
    private final HttpStatus statusCode;

    public UnAuthroizedException(String body, HttpStatus statusCode) {
        super(body);
        this.statusCode = statusCode;
    }


}
