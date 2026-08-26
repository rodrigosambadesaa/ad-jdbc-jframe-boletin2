package dev.rodrigosambade.swing;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExerciseLogicTest {

    @Test
    void calculatorAddsNumbers() {
        BigDecimal result = ExerciseLogic.calculate(
                new BigDecimal("2"),
                new BigDecimal("3"),
                "+");

        assertEquals(new BigDecimal("5"), result);
    }

    @Test
    void factorialCalculatesProductDownToOne() {
        assertEquals(BigInteger.valueOf(120), ExerciseLogic.factorial(5));
    }

    @Test
    void calculatorRejectsDivisionByZero() {
        assertThrows(
                ArithmeticException.class,
                () -> ExerciseLogic.calculate(
                        BigDecimal.ONE,
                        BigDecimal.ZERO,
                        "/"));
    }
}
