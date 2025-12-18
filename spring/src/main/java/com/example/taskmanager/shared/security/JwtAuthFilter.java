package com.example.taskmanager.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String requestURI = request.getRequestURI();
        
        // Skip filtering for public endpoints
        return path.startsWith("/api/auth") || requestURI.contains("/api/auth") ||
               path.startsWith("/v3/api-docs") || requestURI.contains("/v3/api-docs") ||
               path.startsWith("/swagger-ui") || requestURI.contains("/swagger-ui") ||
               path.startsWith("/swagger-ui.html") || requestURI.contains("/swagger-ui.html") ||
               path.startsWith("/swagger-resources") || requestURI.contains("/swagger-resources") ||
               path.startsWith("/webjars") || requestURI.contains("/webjars") ||
               path.startsWith("/actuator") || requestURI.contains("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
    
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // no authentication, just continue
            filterChain.doFilter(request, response);
            return;
        }
    
        String token = authHeader.substring(7);
        String subject = jwtService.extractSubject(token);
    
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Long userId = Long.parseLong(subject);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NumberFormatException e) {
                // invalid token, skip authentication
            }
        }
    
        filterChain.doFilter(request, response);
    }
    
    
}


