package com.example.group.project.util;

import com.example.group.project.model.entity.Purchase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PurchaseUtil {

    public static LocalDate getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String newDate = formatter.format(date);
        return LocalDate.parse(newDate);
    }

}
