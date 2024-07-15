package com.example.group.project.controller;


import com.example.group.project.model.Purchase;
import com.example.group.project.model.PurchaseRepository;
import com.example.group.project.model.Record;
import com.example.group.project.model.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RecordRepository recordRepository;


    // POST endpoint to make purchase
    @PostMapping("/makePurchase")
    @Transactional
    public String makePurchase(@RequestBody Purchase newPurchase){
        log.info("Attempting to make new purchase: " + newPurchase);

        // get record id from the input:
        Long recordID = newPurchase.getRecordLink().getId();
        log.info("The purchase ID is: " + recordID);

        // check quantity & adjust if in stock:
        Record newRecord = recordRepository.getReferenceById(recordID);
        int newQuant = newRecord.getQuantity();
        if (newQuant == 0) {
            return "This item is not in stock";
        } else {
            newRecord.setQuantity(newQuant - 1);
        }

        // save purchase:
        purchaseRepository.save(newPurchase);
        log.info("Purchase made");
        return "Your purchase has been made";
    }


}
