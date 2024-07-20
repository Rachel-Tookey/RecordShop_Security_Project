package com.example.group.project.controller;

import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import static com.example.group.project.util.PurchaseUtil.getDate;

@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RecordRepository recordRepository;



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

        if (!checkIdExists(userPurchase)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This is not a valid item id");
        } else if (!checkStock(userPurchase)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not in stock");
        }

        Long purchaseID = commitPurchase(userPurchase);

        if (checkSuccess(purchaseID)) {
            return ResponseEntity.ok("Purchase successful! Purchase ID " + purchaseID);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something has gone wrong");
        }
    }


    // non-endpoint methods:

    @Transactional
    public Long commitPurchase(Map<String, Object>userPurchase){

        double itemPrice = adjustPrice(userPurchase);

        Record newRecord = recordRepository.getReferenceById(pullID(userPurchase));
        int newQuant = newRecord.getQuantity();
        newRecord.setQuantity(newQuant - 1);

        String customerName = userPurchase.get("customer").toString();

        Purchase newPurchase = Purchase.builder()
                .customer(customerName)
                .price(itemPrice)
                .date(getDate())
                .recordLink(newRecord)
                .build();

        purchaseRepository.save(newPurchase);

        log.info("Purchase made");

        return newPurchase.getId();
    };


    public boolean checkSuccess(Long purchaseID){
        return purchaseRepository.existsById(purchaseID);
    }


    public Long pullID(Map<String, Object>userPurchase) {
        return (Long) userPurchase.get("id");
    }

    public boolean checkStock(Map<String, Object>userPurchase){
        Record newRecord = recordRepository.getReferenceById(pullID(userPurchase));
        int newQuant = newRecord.getQuantity();
        if (newQuant == 0) {
            return false;
        }
        return true;
    }

    public boolean checkIdExists(Map<String, Object>userPurchase){
        return purchaseRepository.existsById(pullID(userPurchase));
    };


    public double adjustPrice(Map<String, Object>  userPurchase){

        double itemPrice = recordRepository.getReferenceById(pullID(userPurchase)).getPrice();

        if (userPurchase.containsKey("discount")) {
            String userDiscount = (String) userPurchase.get("discount");
            if (userDiscount.equals("CFG")) {
                itemPrice = itemPrice * 0.8;
            }
        }
        return Math.round(itemPrice);
    }

}
