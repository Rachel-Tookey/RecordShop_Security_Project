package com.example.group.project.service.impl;

import com.example.group.project.model.entity.Role;
import com.example.group.project.model.repository.RoleRepository;
import com.example.group.project.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() { return roleRepository.findAll(); }

    @Override
    public boolean checkIdExists(String id){
        return roleRepository.existsById(parseLong(id));
    }

    @Override
    public String getRoleById(String id) {
        try {
            Long role_id = Long.parseLong(id);
            Optional<Role> optionalRole = roleRepository.findById(role_id);
            if (optionalRole.isEmpty()) {
                return "No Role Found with ID " + role_id; }
            return optionalRole.get().getRole();
        }
        catch (NumberFormatException e) {
            String message = "Invalid Role ID format: " + id;
            throw new IllegalArgumentException(message + " " + e);
        }


    }

}
