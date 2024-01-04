package com.app.junit;

import com.app.junit.generator.SimpleDisplayNameGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

// DisplayName could be defined on class, and method
// There are 2 ways to define DisplayName
// 1. Manual define the DisplayName
// @DisplayName("Test for Calculator class")
// 2. Using Display Name Generator
@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
// The @Tags annotation provides flexibility in test execution, allowing us to selectively run tests based on specified tags.
@Tags({
    @Tag("calculator-test")
})
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

    @Test
    public void testAssumptions() {
        String env = "PRODUCTION";
        // will throw TestAbortedException and aborting test after that
        assumeFalse("PRODUCTION".equals(env));
    }

    @DisplayName("Parameterized test for calculator")
    @ParameterizedTest(name = "{displayName} with data {0}")
    // ParameterizedTest supports several value source:
    // @ValueSource, @EnumSource, @MethodSource, @CsvSource, @CsvFileSource, @ArgumentSource
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 45, 34, 56})
    public void testParameterized(int value) {
        var expected = value + value;

        var result = calculator.add(value, value);
        assertEquals(expected, result);
    }
}