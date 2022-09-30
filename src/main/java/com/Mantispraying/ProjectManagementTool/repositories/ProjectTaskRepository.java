package com.Mantispraying.ProjectManagementTool.repositories;

import com.Mantispraying.ProjectManagementTool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
}
