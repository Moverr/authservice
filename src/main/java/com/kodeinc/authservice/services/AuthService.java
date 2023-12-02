package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */
public interface AuthService {

    AuthResponse refresh(HttpServletRequest request);
      AuthResponse authenticate(HttpServletRequest request);
     AuthResponse authenticate(LoginRequest request);
}
