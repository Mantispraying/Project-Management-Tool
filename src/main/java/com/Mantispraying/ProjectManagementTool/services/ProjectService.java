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
}
