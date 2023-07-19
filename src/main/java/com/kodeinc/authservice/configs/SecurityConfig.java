package com.kodeinc.authservice.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .authorizeRequests()
                 .anyRequest()
                 .authenticated();

//         http.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
//             @Override
//             public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
//                 //todo:
//             }
//         });
         http.httpBasic(new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
             @Override
             public void customize(HttpBasicConfigurer<HttpSecurity> httpSecurityHttpBasicConfigurer) {
                 httpSecurityHttpBasicConfigurer.realmName("mover");
             }
         });


         return http.build();
    }
}
