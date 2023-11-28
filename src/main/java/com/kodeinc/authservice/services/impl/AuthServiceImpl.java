package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.dtos.responses.RoleResponse;
import com.kodeinc.authservice.dtos.responses.UserResponse;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
    private AuthenticationManager authenticationManager;


    @Override
    public AuthResponse authenticate(LoginRequest request) {
       validateAuthentication(request);
         final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user == null) {
            throw new KhoodiUnAuthroizedException("Invalid username or password");
        }

        return populate(user);
    }

    private Authentication validateAuthentication(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (!authentication.isAuthenticated())
            throw new KhoodiUnAuthroizedException("Un Authorized Access");

        return authentication;
    }

    private AuthResponse populate(UserDetails user) {

        String token = JwtUtils.generateToken(user);
        String refreshToken = JwtUtils.refreshJwtToken(token);


        UserResponse userResponse = new UserResponse();
        //userResponse.setPermissions(use);
        userResponse.setUsername(user.getUsername());

        userResponse.setRoles(
                user.getAuthorities().stream().map(item ->{
                    RoleResponse  role =  new RoleResponse();
                    role.setRole(item.toString());
                    return  role;
                }).collect(Collectors.toList())
        );


        AuthResponse response =  new AuthResponse();
        response.setMessage("Logged in succesfuly");
        response.setSuccess(user.isEnabled());
        response.setAuthToken(token);
        response.setRefreshToken(refreshToken);
        response.setUser(userResponse);

        return response;
    }

}
