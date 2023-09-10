package com.kodeinc.authservice.services;

import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */
public interface AuthService {

    public AuthResponse authenticate(LoginRequest request);
}
