package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.dtos.responses.AuthResponse;
import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController<AuthResponse>{


    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private final JwtUtils jwtUtils;

    @GetMapping
    public  ResponseEntity<String> authenticate(){
       return ResponseEntity.ok("TEsted");
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody LoginRequest loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {

            final UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            if (user != null) {
                return ResponseEntity.ok(jwtUtils.generateToken(user));
            }

            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
