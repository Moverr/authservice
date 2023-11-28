package com.kodeinc.authservice.configs;

import com.kodeinc.authservice.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author Muyinda Rogers
 * @Date 2023-08-31
 * @Email moverr@gmail.com
 */
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString().trim();
        String hashedPassword = passwordEncoder.encode(password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String savedPassword = userDetails.getPassword();


        if (passwordEncoder().matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
