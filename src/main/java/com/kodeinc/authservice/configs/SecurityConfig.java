package com.kodeinc.authservice.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Autowired
    private   JwtAthFilter jwtAthFilter;
    @Autowired
    private   UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         http.csrf(csrf -> csrf.disable()) //todo:implementation later
                 .authorizeHttpRequests( auth-> auth
                         .requestMatchers
                                 ("/actuator/**","/api/v1/auth/**","/api/v1/projects/**")

                         .permitAll()
                         .requestMatchers("/").denyAll()
                         .anyRequest().authenticated()
                 )


                 .sessionManagement(
                         sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
                 .authenticationProvider(authProvider())
                 .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();
    }






    @Bean
    public AuthenticationProvider authProvider() {

        final CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false); //disable caching
        //  provider.setPostAuthenticationChecks(differentLocationChecker());
        return provider;


       /* final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
      //  provider.setPostAuthenticationChecks(differentLocationChecker());
        return provider;
        */
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//    return  new UserDetailsService() {
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            return userDAO.findUserByEmail(username);
//        }
//    };
//    }
}
