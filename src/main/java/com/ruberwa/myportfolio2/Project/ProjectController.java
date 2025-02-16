package com.ruberwa.myportfolio2.Project;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("api/project")
@Validated
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    Flux<ProjectResponseModel> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping()
    Mono<ProjectResponseModel> addProject(@RequestBody Mono<ProjectRequestModel> projectRequestModel) {
        return projectService.AddProject(projectRequestModel);
    }

    @PutMapping("/{projectId}")
    Mono<ProjectResponseModel> updateProject(@PathVariable String projectId, @RequestBody Mono<ProjectRequestModel> projectRequestModel) {
        return projectService.EditProject(projectRequestModel, projectId);
    }

    @GetMapping("/{projectId}")
    Mono<ProjectResponseModel> getProjectById(@PathVariable String projectId) {
        return projectService.GetProject(projectId);
    }
}

