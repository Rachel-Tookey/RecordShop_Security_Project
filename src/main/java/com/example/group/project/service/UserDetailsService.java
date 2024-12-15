package com.example.group.project.service;
import com.example.group.project.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);

    List<User> getAllUsers();

    User findByUsername(String username);
}
