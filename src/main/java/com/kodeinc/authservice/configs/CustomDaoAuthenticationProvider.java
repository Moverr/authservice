package com.kodeinc.authservice.configs;

import com.kodeinc.authservice.services.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

/**
 * @author Muyinda Rogers
 * @Date 2023-09-13
 * @Email moverr@gmail.com
 */


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {



    @Autowired
    private UserDetailServiceImpl _userDetailService;
    @Override

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

            if (authentication.getCredentials() == null) {
                this.logger.debug("Failed to authenticate since no credentials provided");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }


            String presentedPassword = authentication.getCredentials().toString();
            String  savedPassword = userDetails.getPassword();

            if (  !this.getPasswordEncoder().matches(presentedPassword, savedPassword)) {
                this.logger.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }


    }


}