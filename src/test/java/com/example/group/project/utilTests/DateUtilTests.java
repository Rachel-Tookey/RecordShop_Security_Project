package com.example.group.project.utilTests;

import com.example.group.project.util.DateUtil;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTests {

    @Test
    public void getDate_Called_CorrectType() {
        Object testDate = DateUtil.getDate();
        assertInstanceOf(LocalDate.class, testDate);
    }

    @Test
    public void getDate_Called_CorrectDate() {
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = DateUtil.getDate();
        assertEquals(expectedDate, actualDate);
    }


}
