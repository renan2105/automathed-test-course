package br.com.renan.business;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListTest {

    @Test
    void testMockingList_When_SizeIsCalled_ShouldReturnMultipleValues() {
//        Given/Arrange
        var list = mock(List.class);
        when(list.size()).thenReturn(10).thenReturn(20);

//        When/Act & Then/Assert
        assertEquals(10, list.size());
        assertEquals(20, list.size());
        assertEquals(20, list.size());
    }

    @Test
    void testMockingList_When_GetIsCalled_ShouldReturnRenan() {
//        Given/Arrange
        var list = mock(List.class);
        when(list.get(0)).thenReturn("Renan");

//        When/Act & Then/Assert
        assertEquals("Renan", list.get(0));
        assertNull(list.get(1));
    }

    @Test
    void testMockingList_When_GetIsCalledWithArgumentMatcher_ShouldReturnRenan() {
//        Given/Arrange
        var list = mock(List.class);
        when(list.get(anyInt())).thenReturn("Renan");

//        When/Act & Then/Assert
        assertEquals("Renan", list.get(anyInt()));
        assertEquals("Renan", list.get(anyInt()));
    }

    @Test
    void testMockingList_When_ThrowsAnException() {
//        Given/Arrange
        var list = mock(List.class);
        when(list.get(anyInt())).thenThrow(new RuntimeException("Foo Bar!!"));

//        When/Act & Then/Assert
        assertThrows(RuntimeException.class, () -> {
            list.get(anyInt());
        }, () -> "Should have throw an RuntimeException");
    }
}
