package com.Mantispraying.ProjectManagementTool.services;

import com.Mantispraying.ProjectManagementTool.domain.Backlog;
import com.Mantispraying.ProjectManagementTool.domain.Project;
import com.Mantispraying.ProjectManagementTool.domain.User;
import com.Mantispraying.ProjectManagementTool.exceptions.ProjectIdException;
import com.Mantispraying.ProjectManagementTool.exceptions.ProjectNotFoundException;
import com.Mantispraying.ProjectManagementTool.repositories.BacklogRepository;
import com.Mantispraying.ProjectManagementTool.repositories.ProjectRepository;
import com.Mantispraying.ProjectManagementTool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username){
        try{
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }
        catch (Exception ex){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId, String username){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project==null){
            throw new ProjectIdException("Project ID '"+projectId.toUpperCase()+"' doesn't exist");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectid, String username){
        projectRepository.delete(findProjectByIdentifier(projectid,username));
    }
}
