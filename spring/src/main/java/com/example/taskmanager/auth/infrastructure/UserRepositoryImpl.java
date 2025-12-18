package com.example.taskmanager.auth.infrastructure;

import com.example.taskmanager.auth.domain.User;
import com.example.taskmanager.auth.domain.UserId;
import com.example.taskmanager.auth.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(entity -> new User(
                        new UserId(entity.getId()),
                        entity.getEmail(),
                        entity.getPasswordHash()
                ));
    }
}


