package com.app.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void testAddSuccess() {
        System.out.println("Running testAddSuccess");
        var result = calculator.add(10, 10);
        // assertEquals is used for assert the actual value with our expectation
        assertEquals(20, result);
    }

    @Test
    public void testDivideSuccess() {
        System.out.println("Running testDivideSuccess");
        var result = calculator.divide(20, 5);
        assertEquals(4, result);
    }

    @Test
    public void testDivideFailed() {
        System.out.println("Running testDivideFailed");
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
    }
}
