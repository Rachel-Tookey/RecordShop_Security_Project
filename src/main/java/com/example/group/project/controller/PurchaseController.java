package com.example.group.project.controller;


import com.example.group.project.model.Purchase;
import com.example.group.project.model.PurchaseRepository;
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

    // POST endpoint to make purchase
    @PostMapping("/makePurchase")
    public String makePurchase(@RequestBody Purchase newPurchase){
        log.trace("Attempting to add new book: " + newPurchase);

        // need method here to reduce quantity of relevant item?
        // can that method also check the quantity of the relevant items?

        // use build?

        // validation to return different error messages?

        purchaseRepository.save(newPurchase);
        log.trace("Book added");
        return "Your book has been added";
    }


}
