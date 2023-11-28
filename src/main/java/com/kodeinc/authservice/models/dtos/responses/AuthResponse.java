package com.kodeinc.authservice.models.dtos.responses;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonSerialize
public class AuthResponse {
    private String username;
    private AuthTokenResponse authToken;
    private RefreshTokenResponse refreshToken;
    private boolean accountLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    //todo: profile information missing :: probaly not important at the moment ..


}
