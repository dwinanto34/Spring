package com.app.junit;

import com.app.junit.generator.SimpleDisplayNameGenerator;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// DisplayName could be defined on class, and method
// There are 2 ways to define DisplayName
// 1. Manual define the DisplayName
// @DisplayName("Test for Calculator class")
// 2. Using Display Name Generator
@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
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
    // Manual defined DisplayName has higher priority
    // and will overwrite the DisplayNameGenerator
    @DisplayName("Test scenario for method add(Integer, Integer)")
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
    // To disable particular scenario test
    @Disabled
    public void testDivideFailed() {
        System.out.println("Running testDivideFailed");
        // assertThrows is used for assert our exception expectation with the actual exception
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
    }

    @Test
    public void testAssumptions() {
        String env = "PRODUCTION";
        // will throw TestAbortedException and aborting test after that
        assumeFalse("PRODUCTION".equals(env));
        assumeTrue(!"PRODUCTION".equals(env));
    }
}
