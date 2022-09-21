package com.Mantispraying.ProjectManagementTool.repositories;

import com.Mantispraying.ProjectManagementTool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
}
