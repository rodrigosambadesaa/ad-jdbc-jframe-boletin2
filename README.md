# Boletín 2 — Swing (Acceso a Datos)

Reimplementación moderna de los ejercicios `Ejercicio1` a `Ejercicio16` conservados en la entrega de 2018.

## Qué conserva

1. Minicalculadora.
2. Valor absoluto.
3. Factorial.
4. Potencia.
5. Longitud de una cadena.
6. Comprobar si una suma supera 10.
7. Clasificar un número como positivo, negativo o cero.
8. Comparar dos caracteres.
9. Comprobar el intervalo `[10, 100]`.
10. Potencias de 2 entre los exponentes 0 y 10.
11. Múltiplos de 4 hasta `N`.
12. Saludo mediante cuadro de diálogo.
13. Encuesta sencilla.
14. Lista de alumnos.
15. Dos listas con transferencia de alumnos.
16. Selector de color.

## Mejoras

- Java 21 y Maven.
- `ActionListener` en lugar de `MouseListener` para botones.
- Layout managers: no hay coordenadas absolutas.
- Validación de entrada centralizada.
- División por cero controlada y `BigDecimal` con `MathContext.DECIMAL64`.
- El factorial usa `BigInteger`.
- Lógica pura separada de Swing y cubierta por JUnit.

Ejecute `dev.rodrigosambade.swing.App`.
