package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Override
    public AuthResponse authenticate(LoginRequest request) {

        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user != null) {
            return  populate(user);
        }

        return null;
    }

    private AuthResponse populate(UserDetails user){

        //todo: generate token.
        // todo : add user details

        return  null;
    }

}
