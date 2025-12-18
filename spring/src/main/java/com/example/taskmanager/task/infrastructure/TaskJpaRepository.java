package com.example.taskmanager.task.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, Long> {

    List<TaskJpaEntity> findByProjectId(Long projectId);

    Optional<TaskJpaEntity> findByIdAndProjectId(Long id, Long projectId);

    void deleteByIdAndProjectId(Long id, Long projectId);
}


