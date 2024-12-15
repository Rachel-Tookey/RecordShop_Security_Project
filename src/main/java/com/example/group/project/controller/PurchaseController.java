package com.example.group.project.controller;


import com.example.group.project.service.impl.PurchaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    public PurchaseServiceImpl purchaseServiceImpl;

    // POST endpoint to make purchase
    @PostMapping("/purchase")
    public ResponseEntity<?> makePurchase(@RequestBody Map<String, Object> userPurchase){
        log.info("Attempting to make new purchase");

        if (!userPurchase.containsKey("customer")) {
            log.info("Customer name not provided");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name not provided");
        }

        if (userPurchase.get("customer").toString().length() < 3) {
            log.info("Customer name too short");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name too short");
        } else if (!userPurchase.containsKey("id")) {
            log.info("No Id provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id provided");
        }

        Pattern numericalPattern = Pattern.compile("^\\d+$");
        Matcher matcher = numericalPattern.matcher(userPurchase.get("id").toString());
        if (!matcher.find()) {
            log.info("Id not numerical value");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be numerical value");
        }

        if (!purchaseServiceImpl.checkIdExists(userPurchase)) {
            log.info("ID does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This is not a valid item Id");
        } else if (!purchaseServiceImpl.checkStock(userPurchase)) {
            // Conflict status code has been selected here as in a fully fledged application with a frontend, prior logic should prevent the request getting this far
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
