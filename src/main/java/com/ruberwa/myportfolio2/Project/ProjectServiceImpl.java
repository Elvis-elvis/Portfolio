package com.ruberwa.myportfolio2.Project;

import com.ruberwa.myportfolio2.utils.EntityDTOUtil;
import com.ruberwa.myportfolio2.utils.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Flux<ProjectResponseModel> getAllProjects() {
        return projectRepository.findAll().map(EntityDTOUtil::toProjectResponseModel);
    }

    @Override
    public Mono<ProjectResponseModel> AddProject(Mono<ProjectRequestModel> projectRequestModel) {
        return projectRequestModel
                .map(EntityDTOUtil::toProjectEntity)
                .flatMap(projectRepository::insert)
                .flatMap(savedProject-> projectRepository.findByProjectId(savedProject.getProjectId()))
                .map(EntityDTOUtil::toProjectResponseModel);

    }

    @Override
    public Mono<ProjectResponseModel> EditProject(Mono<ProjectRequestModel> projectRequestModel, String projectId) {
        return projectRepository.findByProjectId(projectId)
                .flatMap(existingProject -> projectRequestModel.map(requestModel->{
                    existingProject.setProjectName(requestModel.getProjectName());
                    existingProject.setIconUrl(requestModel.getIconUrl());
                    existingProject.setGitRepo(requestModel.getGitRepo());
                    existingProject.setSkills(requestModel.getSkills());
                    return existingProject;
                }))
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found with id: " + projectId)))
                .flatMap(projectRepository::save)
                .map(EntityDTOUtil::toProjectResponseModel);
    }

    @Override
    public Mono<ProjectResponseModel> GetProject(String projectId) {
        return projectRepository.findByProjectId(projectId).map(EntityDTOUtil::toProjectResponseModel);
    }

}
