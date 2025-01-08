package com.example.group.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongCheck {

    public static boolean isInLongRange(String stringCheck){
        Pattern numericalPattern = Pattern.compile("^-?\\d{1,18}$");
        Matcher matcher = numericalPattern.matcher(stringCheck);
        return matcher.find();
    }
}
