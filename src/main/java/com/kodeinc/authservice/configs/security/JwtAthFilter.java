package com.kodeinc.authservice.configs.security;

import com.kodeinc.authservice.exceptions.KhoodiUnAuthroizedException;
import com.kodeinc.authservice.helpers.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.kodeinc.authservice.helpers.Constants.INVALID_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class JwtAthFilter extends OncePerRequestFilter {
    public static final int BEGIN_INDEX = 7;
    private final UserDetailsService userDetailsService;

    public JwtAthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, KhoodiUnAuthroizedException {

        try {
            final String authHeader = request.getHeader(AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {


                final String token = authHeader.substring(BEGIN_INDEX);
                final String userName = JwtUtils.extractUsername(token.trim());


                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    System.out.println("username " + userName);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }

            }

            filterChain.doFilter(request, response);
        }
        catch (Exception er){


                if (er instanceof KhoodiUnAuthroizedException){
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(Constants.INVALID_TOKEN);
                }else {

                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.getWriter().write(Constants.INTERNAL_SERVER_ERROR);
                }

        }

    }
}
