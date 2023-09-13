package com.kodeinc.authservice.services.impl;

import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-31
 * @Email moverr@gmail.com
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    static String hashedPassword = passwordEncoder.encode("password");



    private final  static List<UserDetails> MANUAL_USERS = Arrays.asList(
            new User(
                    "moverr@gmail.com",
                    hashedPassword,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            ),
            new User(
                    "user.mail@gmail.com",
                    hashedPassword,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USEr"))
            )

    );

    // Development..
    public  UserDetails findUserByEmail(String username){
        return  this.loadUserByUsername(username);


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  MANUAL_USERS.stream()
                .filter(x-> Objects.equals(x.getUsername(), username))
                .findFirst()
                .orElseThrow( () ->  new KhoodiUnAuthroizedException("User Not found Exception"));

    }
}
