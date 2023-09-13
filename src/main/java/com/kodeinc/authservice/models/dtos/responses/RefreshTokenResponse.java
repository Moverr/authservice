package com.kodeinc.authservice.models.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */
@Getter
@Setter
@Builder
public class RefreshTokenResponse {
    private String refreshToken;
    private String expirationDate;

}
