package com.example.group.project.service;

import com.example.group.project.model.entity.Purchase;
import com.example.group.project.model.entity.Record;
import com.example.group.project.model.repository.PurchaseRepository;
import com.example.group.project.model.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import static com.example.group.project.util.PurchaseUtil.getDate;

@Slf4j
@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RecordRepository recordRepository;

    // checks purchase ID is present in the table
    public boolean checkSuccess(Long purchaseID){
        return purchaseRepository.existsById(purchaseID);
    }

    // takes out record ID json input, converts it to Long record ID:
    public Long pullID(Map<String, Object>userPurchase) {
        Integer recordIDInt = (Integer) userPurchase.get("id");
        Long recordID = recordIDInt.longValue();
        return recordID;
    }

    // checks if the item requested is present in the stock
    public boolean checkStock(Map<String, Object>userPurchase){
        Record newRecord = recordRepository.getReferenceById(pullID(userPurchase));
        int newQuant = newRecord.getQuantity();
        if (newQuant == 0) {
            return false;
        }
        return true;
    }

    // checks if the item requested exists
    public boolean checkIdExists(Map<String, Object>userPurchase){
        return recordRepository.existsById(pullID(userPurchase));
    }

    // gets item price and adjusts it if a valid discount code is provided
    public double adjustPrice(Map<String, Object>  userPurchase){
        double itemPrice = recordRepository.getReferenceById(pullID(userPurchase)).getPrice();
        itemPrice = itemPrice * 100;
        if (userPurchase.containsKey("discount")) {
            String userDiscount = (String) userPurchase.get("discount");
            if (userDiscount.equals("CFG")) {
                itemPrice = itemPrice * 0.8;
            }
        }
        itemPrice = (Math.round(itemPrice));
        return itemPrice / 100;
    }

    // makes the purchase -> adding the purchase table, adjusting stock and returning a purchae ID
    @Transactional
    public Long commitPurchase(Map<String, Object> userPurchase){

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


}
