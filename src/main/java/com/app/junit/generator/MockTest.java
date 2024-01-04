package com.app.junit.generator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class MockTest {
    @Test
    public void testMock() {
        // Mocking is primarily used to simulate the behavior of specific objects in our tests.
        // By defining how the mock behaves, we achieve test isolation, allowing independent test execution.
        List<String> list = mock(List.class);

        when(list.get(0)).thenReturn("Zero");
        when(list.get(100)).thenReturn("Hundred");

        assertEquals("Zero", list.get(0));
        assertEquals("Hundred", list.get(100));

        verify(list, times(1)).get(0);
        verify(list, times(1)).get(100);
    }
}
