package com.example.group.project.util;

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
