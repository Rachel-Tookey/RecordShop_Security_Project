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
import java.util.List;

import java.util.Map;
import static com.example.group.project.util.DateUtil.getDate;

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

    @Override
    public List<Purchase> getPurchases(){
        return purchaseRepository.findAll();
    }


    public Long parseId(String id) {
        return Long.parseLong(id);
    }

    @Override
    public boolean checkStock(String id){
        return recordRepository.getReferenceById(parseId(id)).getQuantity() != 0;
    }

    @Override
    public boolean checkIdExists(String id){
        Long newID = parseId(id);
        return recordRepository.existsById(newID);
    }

    @Override
    public double adjustPrice(double itemPrice, boolean HasDiscount){
        if (HasDiscount) {
           log.info("Discount code applied");
           itemPrice = Math.round(itemPrice * 80);
        } else {
            log.info("No discount code applied");
            itemPrice = Math.round(itemPrice * 100);
        }
        return itemPrice / 100;
    }

    @Override
    public boolean IsDiscount(Map<String, String> userPurchase){
        return userPurchase.containsKey("discount") && userPurchase.get("discount").equals("CFG");
    }

    @Override
    @Transactional
    public Long commitPurchase(Map<String, String> userPurchase){

        Record newRecord = recordRepository.getReferenceById(parseId(userPurchase.get("id")));

        double itemPrice = adjustPrice(newRecord.getPrice(), IsDiscount(userPurchase));

        Purchase newPurchase = Purchase.builder()
                .customer(userPurchase.get("customer"))
                .price(itemPrice)
                .date(getDate())
                .recordLink(newRecord)
                .build();
        purchaseRepository.save(newPurchase);

        log.info("Purchase made");

        int newQuant = newRecord.getQuantity() - 1;
        newRecord.setQuantity(newQuant);

        log.info("Record table adjusted");

        return newPurchase.getId();
    }


}
