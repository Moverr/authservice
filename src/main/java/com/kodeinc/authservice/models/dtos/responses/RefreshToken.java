package com.kodeinc.authservice.models.dtos.responses;

import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */
@AllArgsConstructor
public class RefreshToken {
    private String refreshToken;
    private Date expirationDate;

}
