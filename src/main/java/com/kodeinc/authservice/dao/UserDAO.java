package com.kodeinc.authservice.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Muyinda Rogers
 * @Date 2023-07-31
 * @Email moverr@gmail.com
 */

@Repository
public class UserDAO {
    private final  static List<UserDetails> MANUAL_USERS = Arrays.asList(
            new User(
                    "moverr@gmail.com",
                    "password",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            ),
            new User(
                    "user.mail@gmail.com",
                    "password",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USEr"))
            )

    );


    public  UserDetails findUserByEmail(String username){
        return  MANUAL_USERS.stream()
                        .filter(x-> Objects.equals(x.getUsername(), username))
                        .findFirst()
                        .orElseThrow( () ->  new UsernameNotFoundException("Bo user found Exception"));


    }
}
