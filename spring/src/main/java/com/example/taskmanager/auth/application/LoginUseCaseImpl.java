package com.example.taskmanager.auth.application;

import com.example.taskmanager.auth.domain.User;
import com.example.taskmanager.auth.domain.UserRepository;
import com.example.taskmanager.shared.exception.UnauthorizedException;
import com.example.taskmanager.shared.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginUseCaseImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String login(LoginCommand command) {
        User user = userRepository.findByEmail(command.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        // Simple password check - in production, use password hashing (BCrypt, etc.)
        // For now, comparing passwordHash directly (not secure, but works for demo)
        if (!user.getPasswordHash().equals(command.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        return jwtService.generateToken(user.getId().getValue().toString());
    }
}

