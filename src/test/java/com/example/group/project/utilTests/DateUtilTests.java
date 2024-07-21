package com.example.group.project.utilTests;

import com.example.group.project.util.DateUtil;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateUtilTests {

    @Test
    public void getDateCalledCorrectType() {
        Object testDate = DateUtil.getDate();
        assertTrue(testDate instanceof LocalDate);
    }

    @Test
    public void getDateCalledCorrectDate() {
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = DateUtil.getDate();
        assertEquals(expectedDate, actualDate);
    }


}
