package com.example.group.project.service.impl;

import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import com.example.group.project.service.PurchaseService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import static com.example.group.project.util.DateUtil.getDate;

// PurchaseServiceImpl class allows us to separate business logic from the Controller class
@Slf4j
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final RecordRepository recordRepository;

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        this.purchaseRepository = purchaseRepository;
    }

    // takes out record ID json input, converts it to Long record ID:
    @Override
    public Long pullId(Map<String, Object>userPurchase) {
        Object userId = userPurchase.get("id");
        if (userId instanceof Long) {
            return (Long) userId;
        } else if (userId instanceof Integer) {
            Integer userIdInt = (Integer) userId;
            return userIdInt.longValue();
        } else {
        throw new IllegalArgumentException("Not correct variable type");
        }
    }

    // checks if the item requested is present in the stock
    @Override
    public boolean checkStock(Map<String, Object>userPurchase){
        return recordRepository.getReferenceById(pullId(userPurchase)).getQuantity() != 0;
    }

    // checks if the item requested exists
    @Override
    public boolean checkIdExists(Map<String, Object>userPurchase){
        Long newID = pullId(userPurchase);
        return recordRepository.existsById(newID);
    }

    // gets item price and adjusts it if a valid discount code is provided
    @Override
    public double adjustPrice(Map<String, Object>  userPurchase){
        double itemPrice = recordRepository.getReferenceById(pullId(userPurchase)).getPrice();
        itemPrice = itemPrice * 100;
        if (userPurchase.containsKey("discount")) {
            String userDiscount = String.valueOf(userPurchase.get("discount"));
            if (userDiscount.equals("CFG")) {
                log.info("Discount code applied");
                itemPrice = itemPrice * 0.8;
            } else {
                log.info("Incorrect discount code supplied");
            }
        } else {
            log.info("No discount code applied");
        }
        itemPrice = (Math.round(itemPrice));
        return itemPrice / 100;
    }

    // makes the purchase -> adding the purchase table, adjusting stock and returning a purchase ID
    @Override
    @Transactional
    public Long commitPurchase(Map<String, Object> userPurchase){

        // getting object fields
        double itemPrice = adjustPrice(userPurchase);
        String customerName = userPurchase.get("customer").toString();
        Record newRecord = recordRepository.getReferenceById(pullId(userPurchase));

        // make new purchase:
        Purchase newPurchase = Purchase.builder()
                .customer(customerName)
                .price(itemPrice)
                .date(getDate())
                .recordLink(newRecord)
                .build();
        purchaseRepository.save(newPurchase);

        log.info("Purchase made");

        // adjust quantity in record table:
        int newQuant = newRecord.getQuantity() - 1;
        newRecord.setQuantity(newQuant);

        log.info("Record table adjusted");

        return newPurchase.getId();
    }


}
