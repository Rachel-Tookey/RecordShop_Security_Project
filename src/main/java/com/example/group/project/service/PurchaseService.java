package com.example.group.project.service;

import com.example.group.project.model.entity.Purchase;

import java.util.List;
import java.util.Map;

public interface PurchaseService {

    Long parseId(String Id);

    boolean checkStock(String Id);

    boolean checkProductIdExists(String Id);

    double adjustPrice(double itemPrice, boolean HasDiscount);

    Long commitPurchase(Map<String,String> userPurchase);

    List<Purchase> getPurchases();

    boolean isDiscount(Map<String, String> userPurchase);

    void deleteById(String id);

    boolean checkPurchaseIdExists(String id);
}
