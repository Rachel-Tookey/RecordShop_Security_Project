package com.example.group.project.controller;


import com.example.group.project.model.Purchase;
import com.example.group.project.model.PurchaseRepository;
import com.example.group.project.model.Record;
import com.example.group.project.model.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RecordRepository recordRepository;


    // POST endpoint to make purchase
    @Transactional
    @PostMapping("/makePurchase")
    public ResponseEntity<?> makePurchase(@RequestBody Map<String, Object> userPurchase){
        log.info("Attempting to make new purchase:");

        // get record id from the input:
        Integer recordIDInt = (Integer) userPurchase.get("id");
        Long recordID = recordIDInt.longValue();

        if (!userPurchase.containsKey("customer")) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer name not provided");
        }
        // get customer name:
        String customerName = userPurchase.get("customer").toString();

        if (customerName.length() < 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer name too short");
        }

        // check item id exists:
        if (!recordRepository.existsById(recordID)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This is not a valid item id");
        }

        // check quantity & adjust if in stock:
        Record newRecord = recordRepository.getReferenceById(recordID);
        int newQuant = newRecord.getQuantity();
        if (newQuant == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not in stock");
        } else {
            newRecord.setQuantity(newQuant - 1);
        }

        // get item price and apply discount
        double itemPrice = recordRepository.getReferenceById(recordID).getPrice();

        if (userPurchase.containsKey("discount")) {
            String userDiscount = (String) userPurchase.get("discount");
            if (userDiscount.equals("CFG")) {
                itemPrice = itemPrice * 0.8;
            }
        }

        // getting today's date:
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String newDate = formatter.format(date);

        // save purchase:
        Purchase newPurchase = Purchase.builder()
                .customer(customerName)
                .price(itemPrice)
                .date(LocalDate.parse(newDate))
                .recordLink(newRecord)
                .build();
        purchaseRepository.save(newPurchase);
        log.info("Purchase made");
        return ResponseEntity.ok("Purchase successful!");
    }


}
