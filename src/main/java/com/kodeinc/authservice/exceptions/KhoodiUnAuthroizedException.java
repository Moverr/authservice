package com.kodeinc.authservice.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Setter
@Getter
public class KhoodiUnAuthroizedException extends RuntimeException{
    private  String body;

    public KhoodiUnAuthroizedException(String body) {
        this.body = body;
    }



}
