package com.example.group.project.service;

import java.util.Map;

public interface PurchaseService {

    Long pullId(Map<String,Object> userPurchase);

    boolean checkStock(Map<String,Object> userPurchase);

    boolean checkIdExists(Map<String,Object> userPurchase);

    double adjustPrice(Map<String,Object> userPurchase);

    Long commitPurchase(Map<String,Object> userPurchase);

}
