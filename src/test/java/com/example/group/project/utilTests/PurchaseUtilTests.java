package com.example.group.project.utilTests;

import com.example.group.project.util.PurchaseUtil;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PurchaseUtilTests {

    @Test
    public void getDateCalledCorrectType() {
        Object testDate = PurchaseUtil.getDate();
        assertTrue(testDate instanceof LocalDate);
    }

    @Test
    public void getDateCalledCorrectDate() {
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = PurchaseUtil.getDate();
        assertEquals(expectedDate, actualDate);
    }


}
