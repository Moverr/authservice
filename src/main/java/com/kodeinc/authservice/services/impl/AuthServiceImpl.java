package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.dtos.responses.ErrorResponse;
import com.kodeinc.authservice.exceptions.UnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public AuthResponse authenticate(LoginRequest request) {
        validateAuthentication(request);
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user == null) {
            throw new UnAuthroizedException(ErrorResponse.builder().code("invalid").msg("Invalid username or passord").build());
        }

        return populate(user);
    }

    private Authentication validateAuthentication(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated())
            throw new UnAuthroizedException(ErrorResponse.builder().code("invalid").msg("Un Authorized Access").build());

        return authentication;
    }

    private AuthResponse populate(UserDetails user) {

        String token = jwtUtils.generateToken(user);

        //todo: generate token.
        // todo : add user details

        AuthResponse response = AuthResponse.builder()
                .authToken(token)
                .build();

        return response;
    }

}
