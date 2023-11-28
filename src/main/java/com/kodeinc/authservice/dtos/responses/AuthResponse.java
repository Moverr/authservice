package com.kodeinc.authservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Getter
@Setter
public class AuthResponse {
    @JsonProperty("is_successful")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("user")
    private UserResponse user;
    @JsonProperty("auth_token")
    private String authToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
