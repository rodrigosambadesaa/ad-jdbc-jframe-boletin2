package dev.rodrigosambade.swing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public final class ExerciseLogic {

    private ExerciseLogic() {
    }

    public static BigDecimal calculate(BigDecimal left, BigDecimal right, String operator) {
        return switch (operator) {
            case "+" -> left.add(right);
            case "-" -> left.subtract(right);
            case "*" -> left.multiply(right);
            case "/" -> divide(left, right);
            default -> throw new IllegalArgumentException(
                    "Operación no soportada: " + operator);
        };
    }

    public static BigInteger factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("El factorial requiere n >= 0");
        }

        BigInteger result = BigInteger.ONE;
        for (int factor = 2; factor <= number; factor++) {
            result = result.multiply(BigInteger.valueOf(factor));
        }
        return result;
    }

    public static List<Integer> powersOfTwo() {
        List<Integer> values = new ArrayList<>(11);
        for (int exponent = 0; exponent <= 10; exponent++) {
            values.add(1 << exponent);
        }
        return List.copyOf(values);
    }

    public static List<Integer> multiplesOfFour(int limit) {
        List<Integer> values = new ArrayList<>();
        for (int value = 4; value <= limit; value += 4) {
            values.add(value);
        }
        return List.copyOf(values);
    }

    private static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        if (divisor.signum() == 0) {
            throw new ArithmeticException("División por cero");
        }
        return dividend.divide(divisor, MathContext.DECIMAL64);
    }
}
