package com.example.group.project.utilTests;

import org.junit.jupiter.api.Test;
import static com.example.group.project.util.LongCheck.isInLongRange;
import static org.junit.jupiter.api.Assertions.*;

public class LongCheckTests {

    @Test
    public void CorrectInput_Called_True() {
        boolean testInput = isInLongRange("2");
        assertTrue(testInput);
    }

    @Test
    public void IncorrectInput_Called_False() {
        boolean testInput = isInLongRange("Two");
        assertFalse(testInput);
    }

    @Test
    public void NegativeInput_Called_True() {
        boolean testInput = isInLongRange("-2");
        assertTrue(testInput);
    }

    @Test
    public void LargeInput_Called_True() {
        boolean testInput = isInLongRange("922337203685477580");
        assertTrue(testInput);
    }

    @Test
    public void TooLargeInput_Called_False() {
        boolean testInput = isInLongRange("9223372036854775809");
        assertFalse(testInput);
    }

}
