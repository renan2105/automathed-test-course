package br.com.renan.math;

import org.junit.jupiter.api.*;

@DisplayName("Test Math Operations in SimpleMath Class")
public class SimpleMathTest {
    SimpleMath simpleMath;

    @BeforeAll
    static void setup() {
        System.out.println("Running @BeforeAll Method!");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Running @AfterAll Method!");
    }

    @BeforeEach
    void beforeEachMethod() {
        simpleMath = new SimpleMath();
//        System.out.println("Running @BeforeEach Method!");
    }

//    @AfterEach
//    void afterEachMethod() {
//        System.out.println("Running @AfterEach Method!");
//    }

    @Test
    @DisplayName("Test 6.2 + 2 = 8.2")
    void testSum_When_SixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo() {
//        AAA
//        Given/Arrange

        Double firstNumber = 6.2D;
        Double secondNumber = 2D;
        Double expected = 8.2D;

//        When/Act
        Double actual = simpleMath.sum(firstNumber, secondNumber);

//        Then/Assert
        Assertions.assertEquals(expected, actual,
                () -> firstNumber + " + " + secondNumber + " did not produce " + expected + "!");
    }

    @Test
    @DisplayName("Test 6.2 - 2 = 4.1")
    void testSubtraction() {

        Double firstNumber = 6.2D;
        Double secondNumber = 2D;

        Double actual = simpleMath.subtraction(firstNumber, secondNumber);
        Double expected = 4.2D;

        Assertions.assertEquals(expected, actual,
                () -> firstNumber + " - " + secondNumber + " did not produce " + expected + "!");
    }

    @Test
    @DisplayName("Test 6.2 * 2 = 12.4")
    void testMultiplication() {

        Double firstNumber = 6.2D;
        Double secondNumber = 2D;

        Double actual = simpleMath.multiplication(firstNumber, secondNumber);
        Double expected = 12.4D;

        Assertions.assertEquals(expected, actual,
                () -> firstNumber + " * " + secondNumber + " did not produce " + expected + "!");
    }

    //    @Disabled("TODO: We need still work on it!")
    @Test
    @DisplayName("Test Division by zero")
    void testDivision_When_FirstNumberIsDividedByZero_ShouldThrowArithmeticException() {

        Double firstNumber = 6.2D;
        Double secondNumber = 0D;

        var expectedMessage = "Impossible to divide by zero";

        ArithmeticException actual = Assertions.assertThrows(ArithmeticException.class,
                () -> {
                    simpleMath.division(firstNumber, secondNumber);
                },
                () -> "Division by zero should throw an ArithmeticException");

        Assertions.assertEquals(expectedMessage, actual.getMessage(), () -> "Unexpected exception message!");

    }

    @Test
    @DisplayName("Test 6.2 / 2 = 3.1")
    void testDivision() {

        Double firstNumber = 6.2D;
        Double secondNumber = 2D;

        Double actual = simpleMath.division(firstNumber, secondNumber);
        Double expected = 3.1D;

        Assertions.assertEquals(expected, actual,
                () -> firstNumber + " / " + secondNumber + " did not produce " + expected + "!");
    }

    @Test
    @DisplayName("Test mean of 6.2 + 2 = 4.1")
    void testMean() {

        Double firstNumber = 6.2D;
        Double secondNumber = 2D;

        Double actual = simpleMath.mean(firstNumber, secondNumber);
        Double expected = 4.1D;

        Assertions.assertEquals(expected, actual,
                () -> "The mean of " + firstNumber + " and " + secondNumber + " did not produce " + expected + "!");
    }

    @Test
    @DisplayName("Test square root of 6.2 = 2.4899799195977463")
    void testSquareRoot() {

        Double number = 6.2D;

        Double actual = simpleMath.squareRoot(number);
        Double expected = 2.4899799195977463D;

        Assertions.assertEquals(expected, actual,
                () -> "The square root of " + number + " did not produce " + expected + "!");
    }

}
