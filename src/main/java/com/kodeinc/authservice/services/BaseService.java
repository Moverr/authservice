package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-02
 * @Email moverr@gmail.com
 */
public interface BaseService {
     AuthResponse validateAuth(HttpServletRequest request);

}
