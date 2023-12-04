package com.kodeinc.authservice.services;

import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.responses.AuthorizeRequestResponse;
import com.kodeinc.authservice.models.dtos.responses.PermissionResponse;
import com.kodeinc.authservice.models.dtos.responses.RoleResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Muyinda Rogers
 * @Date 2023-12-02
 * @Email moverr@gmail.com
 */
public interface BaseService {
     AuthResponse validateAuth(HttpServletRequest request);
     PermissionResponse authorizeRequestRoles(HttpServletRequest request, List<RoleResponse> expectedRoles) ;

     AuthorizeRequestResponse authorizeRequestPermissions(HttpServletRequest request, List<PermissionResponse> expectedRoles) ;

     }
