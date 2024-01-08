package com.app.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    // Lifecycle
    // Execute at the beginning of the test

    // Execute at the beginning of each of test case
    // Running testDivideFailed
    // Execute at the end of each of test case

    // Execute at the beginning of each of test case
    // Running testAddSuccess
    // Execute at the end of each of test case

    // Execute at the beginning of each of test case
    // Running testDivideSuccess
    // Execute at the end of each of test case

    // Execute at the end of the test

    @BeforeEach
    public void setUp() {
        System.out.println("Execute at the beginning of each of test case");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Execute at the end of each of test case");
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Execute at the beginning of the test");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Execute at the end of the test");
    }

    @Test
    public void testAddSuccess() {
        System.out.println("Running testAddSuccess");
        var result = calculator.add(10, 10);
        // assertEquals is used for assert our expectation with the actual value
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
        // assertThrows is used for assert our exception expectation with the actual exception
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
    }
}
