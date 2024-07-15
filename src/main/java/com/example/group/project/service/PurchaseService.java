package com.example.group.project.service;

import com.example.group.project.model.PurchaseRepository;
import com.example.group.project.model.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RecordRepository recordRepository;



}
