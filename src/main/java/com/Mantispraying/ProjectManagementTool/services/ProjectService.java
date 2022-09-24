package com.Mantispraying.ProjectManagementTool.services;

import com.Mantispraying.ProjectManagementTool.domain.Project;
import com.Mantispraying.ProjectManagementTool.exceptions.ProjectIdException;
import com.Mantispraying.ProjectManagementTool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch (Exception ex){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' doesn't exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Cannot delete project with ID '"+projectId.toUpperCase()+"', this project doesn't exist");
        }

        projectRepository.delete(project);
    }
}
