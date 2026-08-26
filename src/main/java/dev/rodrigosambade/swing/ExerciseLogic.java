package dev.rodrigosambade.swing;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
public final class ExerciseLogic {
    private ExerciseLogic() {
    }
    public static BigDecimal calculate(BigDecimal a, BigDecimal b, String op) {
        return switch (op) {
            case "+" -> a.add(b);
            case "-" -> a.subtract(b);
            case "*" -> a.multiply(b);
            case "/" -> {
                if (b.signum() == 0) throw new ArithmeticException("División por cero");
                yield a.divide(b, MathContext.DECIMAL64);
            }
            default -> throw new IllegalArgumentException("Operación no soportada: " + op);
        }
        ;
    }
    public static BigInteger factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("El factorial requiere n >= 0");
        BigInteger result = BigInteger.ONE;
        for (int i = 2;
        i <= n;
        i++) result = result.multiply(BigInteger.valueOf(i));
        return result;
    }
    public static List<Integer> powersOfTwo() {
        List<Integer> values = new ArrayList<>();
        for (int i = 0;
        i <= 10;
        i++) values.add(1 << i);
        return List.copyOf(values);
    }
    public static List<Integer> multiplesOfFour(int n) {
        List<Integer> values = new ArrayList<>();
        for (int i = 4;
        i <= n;
        i += 4) values.add(i);
        return List.copyOf(values);
    }
}
