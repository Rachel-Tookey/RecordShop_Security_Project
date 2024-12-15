package com.example.group.project.service.impl;


import com.example.group.project.model.entity.Staff;
import com.example.group.project.model.repository.StaffRepository;
import com.example.group.project.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Slf4j
@Service
public class StaffServiceImpl implements StaffService, UserDetailsService {

    private final StaffRepository StaffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.StaffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaff(){
        return StaffRepository.findAll();
    }

    @Override
    public Staff findByUsername(String username){
        return StaffRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff user = findByUsername(username);
        {
            if(user != null) {
                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        }
    }


}
