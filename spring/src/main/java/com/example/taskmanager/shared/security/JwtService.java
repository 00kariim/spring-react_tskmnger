package com.example.taskmanager.shared.security;

public interface JwtService {

    String generateToken(String subject);

    String extractSubject(String token);

    boolean isTokenValid(String token, String subject);
}


