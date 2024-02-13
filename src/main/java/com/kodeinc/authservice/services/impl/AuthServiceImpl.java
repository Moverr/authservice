package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.security.JwtUtils;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.helpers.Constants;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.models.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.responses.UserResponse;
import com.kodeinc.authservice.models.entities.CustomUserDetails;
import com.kodeinc.authservice.services.AuthService;
import com.kodeinc.authservice.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Service
class AuthServiceImpl implements AuthService {

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleService roleService;

    public AuthResponse refresh(HttpServletRequest request) {
        throw new RuntimeException("Not yet implemented");
    }


    public AuthResponse authenticate(HttpServletRequest request) throws KhoodiUnAuthroizedException {

        String token = JwtUtils.extractToken(request);

        if (token != null && JwtUtils.validateToken(token)) {
            final String userName = JwtUtils.extractUsername(token.trim());
            final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (userDetails == null) {
                throw new KhoodiUnAuthroizedException("Invalid username or password");
            }
            return populate(userDetails);
        } else
            throw new KhoodiUnAuthroizedException("Invalid or missing token");

    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        validateAuthentication(request);
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (userDetails == null) {
            throw new KhoodiUnAuthroizedException("Invalid username or password");
        }
        return populate(userDetails);
    }

    private void validateAuthentication(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (!authentication.isAuthenticated())
            throw new KhoodiUnAuthroizedException("Un Authorized Access");

    }

    private AuthResponse populate(CustomUserDetails user) {

        String token = JwtUtils.generateToken(user);
        String refreshToken = JwtUtils.refreshJwtToken(token);


        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());

        userResponse.setRoles(
                user.getCustomRoles().stream().map(roleService::populate).collect(Collectors.toList())

        );


        AuthResponse response = new AuthResponse();
        response.setMessage(Constants.SUCCESSFUL_LOGIN_MSG);
        response.setSuccess(user.isEnabled());
        response.setAuthToken(token);
        response.setRefreshToken(refreshToken);
        response.setUser(userResponse);

        return response;
    }

}
