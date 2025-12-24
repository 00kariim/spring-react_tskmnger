package com.example.taskmanager.task.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, Long> {

    List<TaskJpaEntity> findByProjectId(Long projectId);

    Optional<TaskJpaEntity> findByIdAndProjectId(Long id, Long projectId);

    void deleteByIdAndProjectId(Long id, Long projectId);

    // Pagination support
    Page<TaskJpaEntity> findByProjectId(Long projectId, Pageable pageable);

    // Search by projectId and query in title/description
    List<TaskJpaEntity> findByProjectIdAndTitleContainingIgnoreCaseOrProjectIdAndDescriptionContainingIgnoreCase(
            Long projectId1, String title, Long projectId2, String description);

    // Search by projectId, title/description and completion status
    List<TaskJpaEntity> findByProjectIdAndCompletedAndTitleContainingIgnoreCaseOrProjectIdAndCompletedAndDescriptionContainingIgnoreCase(
            Long projectId1, boolean completed1, String title, Long projectId2, boolean completed2, String description);
}


