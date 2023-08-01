package com.kodeinc.authservice.configs;

import com.kodeinc.authservice.dao.UserDAO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class JwtAthFilter extends OncePerRequestFilter {
    public static final int BEGIN_INDEX = 7;


    @Autowired
    UserDAO userDAO;

    @Autowired
    private JwtUtils jwtUtils;

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
        userEmail = jwtUtils.extractUsername(jwtToken);
      if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

          UserDetails userDetails= userDAO.findUserByEmail(userEmail);

          //validate Username and password

         if(jwtUtils.validateToken(jwtToken,userDetails)){
             UsernamePasswordAuthenticationToken authToken =
                     new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
         authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(authToken);
         }
      }
      filterChain.doFilter(request,response);

    }
}
