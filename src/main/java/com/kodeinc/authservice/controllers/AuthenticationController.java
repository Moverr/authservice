package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.configs.JwtUtils;
import com.kodeinc.authservice.dao.UserDAO;
import com.kodeinc.authservice.models.dtos.requests.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-30
 * @Email moverr@gmail.com
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;
    private final UserDAO userDAO;
    private final JwtUtils jwtUtils;

    @GetMapping
    public  ResponseEntity<String> authenticate(){
       return ResponseEntity.ok("TEsted");
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final UserDetails user = userDAO.findUserByEmail(request.getUsername());
        if(user != null){
           return    ResponseEntity.ok(jwtUtils.generateToken(user));
        }

        return ResponseEntity.badRequest().body("Invalid username or password");

    }
}
