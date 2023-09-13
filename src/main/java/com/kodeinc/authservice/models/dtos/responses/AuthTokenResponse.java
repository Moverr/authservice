package com.kodeinc.authservice.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */
@Getter
@Setter
@Builder
public class AuthTokenResponse {
    private String token;
    private String expirationDate;
}
