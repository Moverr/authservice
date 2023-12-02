package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */
public interface AuthService {

      AuthResponse authenticate(String token);
     AuthResponse authenticate(LoginRequest request);
}
