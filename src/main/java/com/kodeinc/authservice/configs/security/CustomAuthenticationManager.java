package com.kodeinc.authservice.configs.security;

import com.kodeinc.authservice.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.kodeinc.authservice.helpers.Utilities.passwordEncoder;


/**
 * @author Muyinda Rogers
 * @Date 2023-08-31
 * @Email moverr@gmail.com
 */
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString().trim();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder().matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }
}
