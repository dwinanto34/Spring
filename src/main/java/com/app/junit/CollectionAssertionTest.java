package com.app.junit;

import com.app.junit.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionAssertionTest {

    @Test
    public void testAssertIterableEquals() {
        List<String> expectedList = Arrays.asList("apple", "banana", "orange");
        List<String> actualList = Arrays.asList("apple", "banana", "orange");

        // In order to be equal, both iterables must return equal elements in the same order
        assertIterableEquals(expectedList, actualList, "Lists should be equal");
    }

    @Test
    public void testAssertIterableObjectEquals() {
        List<Product> expectedList = List.of(
                Product.builder()
                        .id("01")
                        .name("name01")
                        .availableStock(1)
                        .price(BigDecimal.TEN)
                        .build(),
                Product.builder()
                        .id("02")
                        .name("name02")
                        .availableStock(2)
                        .price(BigDecimal.TEN)
                        .build()
        );

        List<Product> actualList = List.of(
                Product.builder()
                        .id("01")
                        .name("name01")
                        .availableStock(1)
                        .price(BigDecimal.TEN)
                        .build(),
                Product.builder()
                        .id("02")
                        .name("name02")
                        .availableStock(2)
                        .price(BigDecimal.TEN)
                        .build()
        );


        // In order to be equal, both iterables must return equal elements in the same order
        assertIterableEquals(expectedList, actualList, "Lists should be equal");

        // Ignoring id field and the order of the element
        assertThat(actualList)
                .usingElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(expectedList);
    }

    @Test
    public void testAssertLinesMatch() {
        List<String> expectedLines = Arrays.asList("Hello", ".*");
        List<String> actualLines = Arrays.asList("Hello", "World");

        // very similar to assertIterableEquals
        // but assertLinesMatch supports regex operations
        assertLinesMatch(expectedLines, actualLines, "Lines should match");
    }

    @Test
    public void testAssertArrayEquals() {
        int[] expectedArray = {1, 2, 3};
        int[] actualArray = {1, 2, 3};

        assertArrayEquals(expectedArray, actualArray, "Arrays should be equal");
    }
}
