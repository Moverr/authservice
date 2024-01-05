package com.kodeinc.authservice.configs.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAthFilter jwtAthFilter;
    @Autowired
    private UserDetailsService uds;
   // http://localhost:8082/openapi/swagger-config
    private static final String[] WHITE_LIST = {
            "/","/actuator/**","/v1/auth","/v1/auth/validate"
           ,"/v3/api-docs" ,"/swagger-ui/index.html","/swagger-ui.html"
            ,"/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "openapi/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/admin").hasAnyAuthority("ALL_FUNCTIONS", "SUPER_ADMIN")
                   // testing out     .requestMatchers(HttpMethod.GET,"/see").hasAnyAuthority("")
                     //   .requestMatchers("api/v1/projects/test").hasAnyAuthority("ALL_FUNCTIONS_CREATE")
                        .anyRequest().authenticated()
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider())

                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
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


}
