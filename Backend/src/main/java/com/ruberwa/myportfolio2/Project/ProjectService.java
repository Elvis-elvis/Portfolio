package com.ruberwa.myportfolio2.Project;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
    Flux<ProjectResponseModel> getAllProjects();
    Mono<ProjectResponseModel> AddProject(Mono<ProjectRequestModel> projectRequestModel);
    Mono<ProjectResponseModel> EditProject(Mono<ProjectRequestModel> projectRequestModel, String projectId);

    Mono<ProjectResponseModel> GetProject(String projectId);
    Mono<Void> deleteProject(String projectId);
}
