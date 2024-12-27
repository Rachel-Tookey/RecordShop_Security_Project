package com.example.group.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberChecker {

    public static boolean isThisANumber(String checkIfNumber){
        Pattern numericalPattern = Pattern.compile("^\\d+$");
        Matcher matcher = numericalPattern.matcher(checkIfNumber);
        return matcher.find();
    }
}
