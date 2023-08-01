package com.kodeinc.authservice.configs;


import com.kodeinc.authservice.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    private final JwtAthFilter jwtAthFilter;
    private final UserDAO userDAO;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http.csrf(csrf -> csrf.disable()) //todo:implementation later
                 .authorizeHttpRequests( auth-> auth
                         .requestMatchers
                                 ("/actuator/**","/api/v1/auth/**")

                         .permitAll()
                         .requestMatchers("/").denyAll()
                         .anyRequest().authenticated()
                 )


                 .sessionManagement(
                         sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
                 .authenticationProvider(authenticationProvider())
                 .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
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
            return userDAO.findUserByEmail(username);
        }
    };
    }
}
