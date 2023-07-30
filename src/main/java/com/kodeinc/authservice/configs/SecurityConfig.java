package com.kodeinc.authservice.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    private final JwtAthFilter jwtAthFilter;

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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeRequests()
                 .anyRequest()
                 .authenticated()
                 .and()
                 .authenticationProvider(authenticationProvider())
                 .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);


                //session management
                http.sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


//         http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
//             @Override
//             public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
//                 //todo:
//             }
//         });

      //   .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.realmName("mover"));

         return http.build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return  new BCryptPasswordEncoder(); //todo password encode.
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return  MANUAL_USERS.stream()
                        .filter(x->x.getUsername() == username)
                        .findFirst()
                        .orElseThrow( () ->  new UsernameNotFoundException("Bo user found Exception"));
            }
        };
    }
}
