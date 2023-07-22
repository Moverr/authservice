package com.kodeinc.authservice.configs;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    private final JwtAthFilter jwtAthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeRequests()
                 .anyRequest()
                 .authenticated()
                         .and()
                 .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)



//         http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
//             @Override
//             public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
//                 //todo:
//             }
//         });

         .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.realmName("mover"));


         return http.build();
    }
}
