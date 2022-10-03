package com.Mantispraying.ProjectManagementTool.repositories;

import com.Mantispraying.ProjectManagementTool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    User getById(Long id);
}
