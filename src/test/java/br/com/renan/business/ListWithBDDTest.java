package br.com.renan.business;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ListWithBDDTest {

    @Test
    void testMockingList_When_SizeIsCalled_ShouldReturnMultipleValues() {
//        Given/Arrange
        var list = mock(List.class);
        given(list.size()).willReturn(10).willReturn(20);

//        When/Act & Then/Assert
        assertThat(list.size(), is(10));
        assertThat(list.size(), is(20));
    }

    @Test
    void testMockingList_When_GetIsCalled_ShouldReturnRenan() {
//        Given/Arrange
        var list = mock(List.class);
        given(list.get(0)).willReturn("Renan");

//        When/Act & Then/Assert
        assertThat(list.get(0), is("Renan"));
        assertThat(list.get(1), is(nullValue()));
    }

    @Test
    void testMockingList_When_GetIsCalledWithArgumentMatcher_ShouldReturnRenan() {
//        Given/Arrange
        var list = mock(List.class);
        given(list.get(anyInt())).willReturn("Renan");

//        When/Act & Then/Assert
        assertThat(list.get(anyInt()), is("Renan"));
    }

    @Test
    void testMockingList_When_ThrowsAnException() {
//        Given/Arrange
        var list = mock(List.class);
        given(list.get(anyInt())).willThrow(new RuntimeException("Foo Bar!!"));

//        When/Act & Then/Assert
        assertThrows(RuntimeException.class, () -> {
            list.get(anyInt());
        }, () -> "Should have throw an RuntimeException");
    }
}
