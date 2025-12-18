package com.example.taskmanager.project.infrastructure;

import com.example.taskmanager.project.domain.Project;
import com.example.taskmanager.project.domain.ProjectId;
import com.example.taskmanager.project.domain.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectJpaRepository jpaRepository;

    public ProjectRepositoryImpl(ProjectJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Project save(Project project) {
        ProjectJpaEntity entity;
        if (project.getId() != null) {
            // Update existing entity
            entity = jpaRepository.findById(project.getId().getValue())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            entity.setOwnerId(project.getOwnerId());
            entity.setTitle(project.getTitle());
            entity.setDescription(project.getDescription());
            entity.setCreatedAt(project.getCreatedAt());
        } else {
            // Create new entity
            entity = new ProjectJpaEntity(
                    project.getOwnerId(),
                    project.getTitle(),
                    project.getDescription(),
                    project.getCreatedAt()
            );
        }
        ProjectJpaEntity saved = jpaRepository.save(entity);
        return new Project(
                new ProjectId(saved.getId()),
                saved.getOwnerId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getCreatedAt()
        );
    }

    @Override
    public List<Project> findByUserId(Long userId) {
        return jpaRepository.findByOwnerId(userId).stream()
                .map(e -> new Project(
                        new ProjectId(e.getId()),
                        e.getOwnerId(),
                        e.getTitle(),
                        e.getDescription(),
                        e.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Project> findByIdAndUserId(Long projectId, Long userId) {
        return jpaRepository.findByIdAndOwnerId(projectId, userId)
                .map(e -> new Project(
                        new ProjectId(e.getId()),
                        e.getOwnerId(),
                        e.getTitle(),
                        e.getDescription(),
                        e.getCreatedAt()
                ));
    }
}


