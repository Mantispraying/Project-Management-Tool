package com.Mantispraying.ProjectManagementTool.services;

import com.Mantispraying.ProjectManagementTool.domain.Backlog;
import com.Mantispraying.ProjectManagementTool.domain.Project;
import com.Mantispraying.ProjectManagementTool.domain.ProjectTask;
import com.Mantispraying.ProjectManagementTool.exceptions.ProjectNotFoundException;
import com.Mantispraying.ProjectManagementTool.repositories.BacklogRepository;
import com.Mantispraying.ProjectManagementTool.repositories.ProjectRepository;
import com.Mantispraying.ProjectManagementTool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        try{
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);

            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;
            backlog.setPTSequence(BacklogSequence);
            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getPriority()==null){
                projectTask.setPriority(3);
            }

            if(projectTask.getStatus()==""||projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        }catch (Exception e){
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+id.toUpperCase()+"' doesn't exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
