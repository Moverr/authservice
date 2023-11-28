package com.kodeinc.authservice.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAthFilter jwtAthFilter;
    @Autowired
    private UserDetailsService uds;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers
                                ("/", "/actuator/**", "/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/admin").hasAnyAuthority("ALL_FUNCTIONS", "SUPER_ADMINs")

                        .anyRequest().authenticated()
                )

                //todo: some implementation needed
                .formLogin(customizer -> customizer
                        .loginPage("/login") // Custom login page
                        .permitAll()
                        .loginProcessingUrl("/login") // URL for form submission
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect after failed login
                        .usernameParameter("username") // Custom parameter names in the login form
                        .passwordParameter("password")
                )


                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider())

                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return new CustomAuthenticationManager();
    }



    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        //  authenticationProvider.setPostAuthenticationChecks(differentLocationChecker());
        return authenticationProvider;
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
