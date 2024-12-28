package com.example.group.project.service;

import com.example.group.project.model.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    boolean checkIdExists(String id);

    String getRoleById(String id);
}
