package com.kodeinc.authservice.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class JwtAthFilter extends OncePerRequestFilter {
    public static final int BEGIN_INDEX = 7;
    private UserDetailsService userDetailsService;
    private JwtUtils jwtUtils;

    public JwtAthFilter(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader= request.getHeader(AUTHORIZATION);

        if(authHeader != null && authHeader.startsWith("Bearer ")) {


            final String  token = authHeader.substring(BEGIN_INDEX);
            final String userName = jwtUtils.extractUsername(token);

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }

        filterChain.doFilter(request,response);

    }
}
