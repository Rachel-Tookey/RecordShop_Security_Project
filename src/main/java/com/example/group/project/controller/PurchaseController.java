package com.example.group.project.controller;

import com.example.group.project.service.impl.PurchaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import static com.example.group.project.util.NumberChecker.isThisANumber;


@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    public PurchaseServiceImpl purchaseServiceImpl;

    @GetMapping("/auth/getPurchases")
    public ResponseEntity<?> getPurchases(){
        log.info("Getting all purchases");
        return ResponseEntity.status(HttpStatus.OK).body(purchaseServiceImpl.getPurchases());
    }

    @DeleteMapping("/auth/deletePurchase")
    public ResponseEntity<?> deletePurchase(@RequestParam("id") String id){
        log.info("Attempting to delete a purchase");

        if (!isThisANumber(id)) {
            log.info("Id not a number");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be numerical value");
        }

        if (!purchaseServiceImpl.checkPurchaseIdExists(id)) {
            log.info("Id is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Purchase Id invalid");
        }

        purchaseServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Purchase deleted");
    }

    @PostMapping("/auth/purchase")
    public ResponseEntity<?> makePurchase(@RequestBody Map<String, String> userPurchase){

        log.info("Attempting to make new purchase");

        if (!userPurchase.containsKey("customer")) {
            log.info("Customer name not provided");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name not provided");
        } else if (userPurchase.get("customer").length() < 3) {
            log.info("Customer name too short");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name too short");
        } else if (userPurchase.get("customer").length() > 40){
            log.info("Customer name too long");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name too long");
        } else if (!userPurchase.containsKey("id")) {
            log.info("No Id provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id provided");
        }

        String productId = userPurchase.get("id");

        if (!isThisANumber(productId)) {
            log.info("Id not numerical value");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be numerical value");
        }

        if (!purchaseServiceImpl.checkProductIdExists(productId)) {
            log.info("Id does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This is not a valid item Id");
        } else if (!purchaseServiceImpl.checkStock(productId)) {
            log.info("Item not in stock");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Item not in stock");
        }

        try {
            Long purchaseId = purchaseServiceImpl.commitPurchase(userPurchase);
            log.info("Purchase successful" + purchaseId);
            return ResponseEntity.ok("Purchase successful! Purchase Id " + purchaseId);
        } catch (Exception e) {
            log.error("Unknown error making purchase");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to complete purchase. Please try again later");
        }
    }

}
