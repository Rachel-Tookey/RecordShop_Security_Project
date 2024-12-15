package com.example.group.project.service.impl;


import com.example.group.project.model.repository.StaffRepository;
import com.example.group.project.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository StaffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.StaffRepository = staffRepository;
    }


}
