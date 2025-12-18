package com.example.taskmanager.project.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

    List<ProjectJpaEntity> findByOwnerId(Long ownerId);

    Optional<ProjectJpaEntity> findByIdAndOwnerId(Long id, Long ownerId);
}


