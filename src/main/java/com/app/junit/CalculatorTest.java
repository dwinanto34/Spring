package com.app.junit;

import com.app.junit.generator.SimpleDisplayNameGenerator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// DisplayName could be defined on class, and method
// There are 2 ways to define DisplayName
// 1. Manual define the DisplayName
// @DisplayName("Test for Calculator class")
// 2. Using Display Name Generator
@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
public class CalculatorTest {
    private Calculator calculator = new Calculator();

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
    // Manual defined DisplayName has higher priority
    // and will overwrite the DisplayNameGenerator
    @DisplayName("Test scenario for method add(Integer, Integer)")
    public void testAddSuccess() {
        System.out.println("Running testAddSuccess");
        var result = calculator.add(10, 10);
        // assertEquals is used for assert the actual value with our expectation
        assertEquals(20, result);
    }

    @Test
    // To disable particular scenario test
    @Disabled
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
