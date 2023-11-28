package com.kodeinc.authservice.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
public class Utilities {

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
