package com.example.group.project.service;
import com.example.group.project.model.entity.Staff;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface StaffService {

    UserDetails loadUserByUsername(String username);

    List<Staff> getAllStaff();

    Staff findByUsername(String username);
}
