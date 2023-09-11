package com.kodeinc.authservice.exceptions;

import com.kodeinc.authservice.dtos.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */
public class UnAuthroizedException extends RuntimeException{
    private  String body;

    public UnAuthroizedException(String body) {
        super(body);
    }


}
