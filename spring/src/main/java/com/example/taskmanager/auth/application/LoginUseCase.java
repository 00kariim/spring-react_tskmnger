package com.example.taskmanager.auth.application;

public interface LoginUseCase {

    /**
     * Performs authentication and returns an access token (e.g. JWT) when successful.
     */
    String login(LoginCommand command);
}


