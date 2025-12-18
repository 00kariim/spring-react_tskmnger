package com.example.taskmanager.auth.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
}


