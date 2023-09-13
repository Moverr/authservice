package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.configs.CustomAuthenticationManager;
import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-10
 * @Email moverr@gmail.com
 */

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public String authenticate(LoginRequest request) {
        validateAuthentication(request.getUsername(), request.getPassword());
        final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        if (user == null)
            throw new KhoodiUnAuthroizedException("User is not  Authorized ");
        return populate(user);
    }

    /*
     *
     * Validate Authentications
     * @Param username
     * @Param password
     */
    public void validateAuthentication(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (!authentication.isAuthenticated())
            throw new KhoodiUnAuthroizedException("Un Authorized Access");

    }

    private String populate(UserDetails user) {

        return jwtUtils.generateToken(user);
    }

}
