package com.pankajsingh.projectmmt.services;

import com.pankajsingh.projectmmt.domain.Project;
import com.pankajsingh.projectmmt.exceptions.ProjectIdException;
import com.pankajsingh.projectmmt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier((projectId.toUpperCase()));
        if(project == null) {
            throw new ProjectIdException("project ID '" + projectId + "' doesn't exist");
        }
        return project;
    }

    public Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null) {
            throw new ProjectIdException("Can't delete project ID '" + projectId + ".' This Project doesn't exist");
        }

        projectRepository.delete(project);
    }
}
