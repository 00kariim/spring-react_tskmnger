package com.example.taskmanager.auth;

import com.example.taskmanager.auth.application.LoginCommand;
import com.example.taskmanager.auth.application.LoginUseCaseImpl;
import com.example.taskmanager.auth.domain.User;
import com.example.taskmanager.auth.domain.UserId;
import com.example.taskmanager.auth.domain.UserRepository;
import com.example.taskmanager.shared.exception.UnauthorizedException;
import com.example.taskmanager.shared.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void login_success_returnsToken() {
        var email = "admin@admin.com";
        var password = "123456";
        var user = new User(new UserId(1L), email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken("1")).thenReturn("token-abc");

        String token = loginUseCase.login(new LoginCommand(email, password));

        assertNotNull(token);
        assertEquals("token-abc", token);
    }

    @Test
    void login_invalidEmail_throwsUnauthorized() {
        when(userRepository.findByEmail("no@one.com")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () ->
                loginUseCase.login(new LoginCommand("no@one.com", "pwd")));
    }

    @Test
    void login_invalidPassword_throwsUnauthorized() {
        var email = "admin@admin.com";
        var user = new User(new UserId(1L), email, "otherpwd");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedException.class, () ->
                loginUseCase.login(new LoginCommand(email, "wrongpwd")));
    }
}
