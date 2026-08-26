package dev.rodrigosambade.swing;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;
class ExerciseLogicTest {
    @Test void calculatorAndFactorial() {
        assertEquals(new BigDecimal("5"), ExerciseLogic.calculate(new BigDecimal("2"), new BigDecimal("3"), "+"));
        assertEquals(BigInteger.valueOf(120), ExerciseLogic.factorial(5));
        assertThrows(ArithmeticException.class, () -> ExerciseLogic.calculate(BigDecimal.ONE, BigDecimal.ZERO, "/"));
    }
}
