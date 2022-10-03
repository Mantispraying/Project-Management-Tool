package com.Mantispraying.ProjectManagementTool.services;

import com.Mantispraying.ProjectManagementTool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
