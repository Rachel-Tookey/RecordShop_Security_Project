package com.example.group.project.service.implementation;

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
import static com.example.group.project.util.PurchaseUtil.getDate;

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
    public Long pullID(Map<String, Object>userPurchase) {
        Integer recordIDInt = (Integer) userPurchase.get("id");
        Long recordID = recordIDInt.longValue();
        return recordID;
    }

    // checks if the item requested is present in the stock
    public boolean checkStock(Map<String, Object>userPurchase){
        Record newRecord = recordRepository.getReferenceById(pullID(userPurchase));
        int newQuant = newRecord.getQuantity();
        return newQuant != 0;
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
            try {
                String userDiscount = (String) userPurchase.get("discount");
                if (userDiscount.equals("CFG")) {
                    itemPrice = itemPrice * 0.8;
                }
            } catch (ClassCastException e) {
                log.error("User discount code could not be cast {}", userPurchase.get("discount"));
            }
        }
        itemPrice = (Math.round(itemPrice));
        return itemPrice / 100;
    }

    // makes the purchase -> adding the purchase table, adjusting stock and returning a purchae ID
    @Transactional
    public Long commitPurchase(Map<String, Object> userPurchase){

        // getting variables
        double itemPrice = adjustPrice(userPurchase);
        String customerName = userPurchase.get("customer").toString();
        Record newRecord = recordRepository.getReferenceById(pullID(userPurchase));

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
