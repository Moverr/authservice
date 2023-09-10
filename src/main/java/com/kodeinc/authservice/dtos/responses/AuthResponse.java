package com.kodeinc.authservice.dtos.responses;

import lombok.Builder;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Builder
public class AuthResponse {
    private boolean success;
    private String message;
    private UserResponse user;
    private String authToken;
    private String refreshToken;
}
