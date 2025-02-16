package com.ruberwa.myportfolio2.Project;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Mono<Project> findByProjectId(String projectId);

}
