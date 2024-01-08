package com.app.junit;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void testMock() {
        // Mocking simulates the behavior of specific objects in tests, ensuring test isolation.
        // By defining mock behavior, we achieve independent and controlled test execution.

        // Creating a mock list
        List<String> list = mock(List.class);

        // Setting up mock behavior
        when(list.get(0)).thenReturn("Zero");
        when(list.get(100)).thenReturn("Hundred");

        // Testing mock behavior
        assertEquals("Zero", list.get(0));
        assertEquals("Hundred", list.get(100));

        // Verifying method calls on the mock
        verify(list, times(1)).get(0);
        verify(list, times(1)).get(100);
    }
}
