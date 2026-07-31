package com.ecommerce.wonders.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.UserRepository;
import com.ecommerce.wonders.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(
        TokenService tokenService, 
        UserRepository userRepository
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }
    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if(token != null) {
            String userId = this.tokenService.validateJWT(token);

            if(!userId.isEmpty()) {
                User user = this.userRepository.findById(Long.parseLong(userId)).orElse(null);

                CustomUserLoginDetails userLoginDetails = new CustomUserLoginDetails(user);
                    
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, userLoginDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
