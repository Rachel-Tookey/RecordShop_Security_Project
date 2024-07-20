package com.example.group.project.controller;


import com.example.group.project.service.PurchaseService;
import com.example.group.project.service.PurchaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    public PurchaseServiceImpl purchaseServiceImpl;

    // POST endpoint to make purchase
    @PostMapping("/makePurchase")
    public ResponseEntity<?> makePurchase(@RequestBody Map<String, Object> userPurchase){
        log.info("Attempting to make new purchase:");

        if (!userPurchase.containsKey("customer")) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer name not provided");
        }

        if (userPurchase.get("customer").toString().length() < 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer name too short");
        }

        if (!purchaseServiceImpl.checkIdExists(userPurchase)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This is not a valid item id");
        } else if (!purchaseServiceImpl.checkStock(userPurchase)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not in stock");
        }

        Long purchaseID = purchaseServiceImpl.commitPurchase(userPurchase);

        if (purchaseServiceImpl.checkSuccess(purchaseID)) {
            return ResponseEntity.ok("Purchase successful! Purchase ID " + purchaseID);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something has gone wrong. Please try again later");
        }
    }

}
