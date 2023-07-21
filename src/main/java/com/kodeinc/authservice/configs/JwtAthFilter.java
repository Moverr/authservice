package com.kodeinc.authservice.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAthFilter extends OncePerRequestFilter {
    public static final int BEGIN_INDEX = 7;
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader= request.getHeader(AUTHORIZATION);
        final  String userEmail;
        final  String jwtToken;

        if(authHeader != "" || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.substring(BEGIN_INDEX);
        userEmail = "something"; //todo: implemented
      if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

          UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
          final boolean isTokenValid = false; //todo: to be implemented
         if(isTokenValid){
             UsernamePasswordAuthenticationToken authToken =
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
         authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(authToken);
         }
      }
      filterChain.doFilter(request,response);


    }
}
