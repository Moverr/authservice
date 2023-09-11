package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    private   AuthenticationManager authenticationManager;

    private   UserDetailsService userDetailsService;

    private   JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public String authenticate(LoginRequest request) {
        Authentication authentication = null;

              authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        if (authentication.isAuthenticated()) {

            final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
            if (user != null) {
                return  populate(user);
                        //ResponseEntity.ok(jwtUtils.generateToken(user));
            }

            return "";
                    //ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            //.body("Invalid username or password");
        }
        else{
            return " ";
                    //ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String populate(UserDetails user){

        return  jwtUtils.generateToken(user);
    }

}
