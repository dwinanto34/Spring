package com.app.junit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// Order by method name
// @TestMethodOrder(MethodOrderer.MethodName.class)
// Random order
// @TestMethodOrder(MethodOrderer.Random.class)
// Will refer to @Order annotation for each test method
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedTest {
    @Test
    @Order(3)
    public void test1() {
        System.out.println("1");
    }

    @Test
    @Order(1)
    public void test2() {
        System.out.println("2");
    }

    @Test
    @Order(2)
    public void test3() {
        System.out.println("3");
    }

    @Test
    @Order(4)
    public void test4() {
        System.out.println("4");
    }
}
