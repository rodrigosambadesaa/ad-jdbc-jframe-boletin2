package dev.rodrigosambade.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public final class App {

    private static final int GAP = 8;

    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::showApplication);
    }

    private static void showApplication() {
        JFrame frame = new JFrame("Boletín 2 — Swing");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(createTabs());
        frame.setSize(760, 520);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private static JTabbedPane createTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("01 Calculadora", calculator());
        tabs.add("02 Absoluto", unary(
                "Valor absoluto",
                value -> new BigDecimal(value).abs().toPlainString()));
        tabs.add("03 Factorial", unary(
                "Factorial",
                value -> ExerciseLogic.factorial(Integer.parseInt(value)).toString()));
        tabs.add("04 Potencia", binary(
                "Potencia",
                (left, right) -> String.valueOf(Math.pow(
                        Double.parseDouble(left),
                        Double.parseDouble(right)))));
        tabs.add("05 Cadena", unary(
                "Longitud",
                value -> "La cadena tiene " + value.length() + " caracteres."));
        tabs.add("06 Suma > 10", binary("Suma > 10", App::sumGreaterThanTen));
        tabs.add("07 Signo", unary("Signo", App::describeSign));
        tabs.add("08 Caracteres", binary("Comparar caracteres", App::compareCharacters));
        tabs.add("09 Intervalo", unary("¿Entre 10 y 100?", App::describeInterval));
        tabs.add("10 Potencias 2", outputOnly(
                "Potencias de 2",
                () -> ExerciseLogic.powersOfTwo().toString()));
        tabs.add("11 Múltiplos 4", unary(
                "Múltiplos de 4",
                value -> ExerciseLogic.multiplesOfFour(Integer.parseInt(value)).toString()));
        tabs.add("12 Saludo", greeting());
        tabs.add("13 Encuesta", survey());
        tabs.add("14 Lista", studentList(false));
        tabs.add("15 Dos listas", studentList(true));
        tabs.add("16 Color", colorSelector());
        return tabs;
    }

    private static JPanel calculator() {
        JPanel body = new JPanel(new GridLayout(0, 2, GAP, GAP));
        JTextField left = new JTextField();
        JTextField right = new JTextField();
        JTextField result = readOnlyField();
        JComboBox<String> operator = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        JButton calculate = new JButton("Calcular");
        JButton clear = new JButton("Borrar");

        calculate.addActionListener(event -> safe(result, () -> ExerciseLogic.calculate(
                new BigDecimal(left.getText().trim()),
                new BigDecimal(right.getText().trim()),
                String.valueOf(operator.getSelectedItem()))
                .stripTrailingZeros()
                .toPlainString()));

        clear.addActionListener(event -> {
            left.setText("");
            right.setText("");
            result.setText("");
            operator.setSelectedIndex(0);
        });

        addRows(
                body,
                "Número 1", left,
                "Operación", operator,
                "Número 2", right,
                "Resultado", result);
        body.add(calculate);
        body.add(clear);
        return wrap(body);
    }

    private static JPanel unary(String title, UnaryOperation operation) {
        JPanel body = new JPanel(new GridLayout(0, 2, GAP, GAP));
        JTextField input = new JTextField();
        JTextField output = readOnlyField();
        JButton calculate = new JButton("Calcular");

        calculate.addActionListener(event -> safe(
                output,
                () -> operation.apply(input.getText())));

        addRows(body, title, input, "Resultado", output);
        body.add(calculate);
        body.add(new JLabel());
        return wrap(body);
    }

    private static JPanel binary(String title, BinaryOperation operation) {
        JPanel body = new JPanel(new GridLayout(0, 2, GAP, GAP));
        JTextField left = new JTextField();
        JTextField right = new JTextField();
        JTextField output = readOnlyField();
        JButton calculate = new JButton("Calcular");

        calculate.addActionListener(event -> safe(
                output,
                () -> operation.apply(left.getText(), right.getText())));

        addRows(
                body,
                title + " — valor 1", left,
                "Valor 2", right,
                "Resultado", output);
        body.add(calculate);
        body.add(new JLabel());
        return wrap(body);
    }

    private static JPanel outputOnly(String title, Supplier<String> operation) {
        JPanel body = new JPanel(new BorderLayout(GAP, GAP));
        JTextArea output = new JTextArea();
        output.setEditable(false);
        JButton show = new JButton("Visualizar");
        show.addActionListener(event -> output.setText(operation.get()));

        body.add(new JLabel(title), BorderLayout.WEST);
        body.add(show, BorderLayout.NORTH);
        body.add(new JScrollPane(output), BorderLayout.CENTER);
        return wrap(body);
    }

    private static JPanel greeting() {
        JPanel body = new JPanel(new FlowLayout());
        JTextField name = new JTextField(20);
        JButton show = new JButton("Mostrar");

        show.addActionListener(event -> {
            String value = name.getText().trim();
            if (value.isEmpty()) {
                error(body, "Debe introducir un nombre");
            } else {
                JOptionPane.showMessageDialog(body, "Hola " + value);
            }
        });

        body.add(new JLabel("Nombre:"));
        body.add(name);
        body.add(show);
        return wrap(body);
    }

    private static JPanel survey() {
        JPanel body = new JPanel(new GridLayout(0, 1, 6, 6));
        JTextField name = new JTextField();
        JRadioButton java = new JRadioButton("Java", true);
        JRadioButton kotlin = new JRadioButton("Kotlin");
        JRadioButton other = new JRadioButton("Otro");
        ButtonGroup group = new ButtonGroup();

        for (JRadioButton option : List.of(java, kotlin, other)) {
            group.add(option);
            body.add(option);
        }

        JButton result = new JButton("Resultado");
        result.addActionListener(event -> JOptionPane.showMessageDialog(
                body,
                "Nombre: " + name.getText().trim()
                        + "\nOpción: " + selectedLanguage(java, kotlin)));

        body.add(new JLabel("Nombre:"));
        body.add(name);
        body.add(result);
        return wrap(body);
    }

    private static JPanel studentList(boolean twoLists) {
        DefaultListModel<String> leftModel = new DefaultListModel<>();
        DefaultListModel<String> rightModel = new DefaultListModel<>();
        JList<String> left = new JList<>(leftModel);
        JList<String> right = new JList<>(rightModel);
        JTextField name = new JTextField(14);
        JButton add = new JButton("Añadir");
        JButton remove = new JButton("Borrar");

        add.addActionListener(event -> addStudent(name, leftModel));
        remove.addActionListener(event -> left
                .getSelectedValuesList()
                .forEach(leftModel::removeElement));

        JPanel controls = new JPanel(new FlowLayout());
        controls.add(new JLabel("Nombre"));
        controls.add(name);
        controls.add(add);
        controls.add(remove);

        JPanel body = new JPanel(new BorderLayout(GAP, GAP));
        body.add(controls, BorderLayout.NORTH);
        body.add(twoLists
                ? twoListPanel(left, right, leftModel, rightModel)
                : new JScrollPane(left), BorderLayout.CENTER);
        return wrap(body);
    }

    private static JPanel twoListPanel(
            JList<String> left,
            JList<String> right,
            DefaultListModel<String> leftModel,
            DefaultListModel<String> rightModel) {
        JButton toRight = new JButton(">>");
        JButton toLeft = new JButton("<<");
        toRight.addActionListener(event -> move(left, leftModel, rightModel));
        toLeft.addActionListener(event -> move(right, rightModel, leftModel));

        JPanel arrows = new JPanel(new GridLayout(2, 1));
        arrows.add(toRight);
        arrows.add(toLeft);

        JPanel panel = new JPanel(new GridLayout(1, 3, GAP, GAP));
        panel.add(new JScrollPane(left));
        panel.add(arrows);
        panel.add(new JScrollPane(right));
        return panel;
    }

    private static JPanel colorSelector() {
        JPanel body = new JPanel(new FlowLayout());
        JTextField field = readOnlyField();
        field.setColumns(12);

        for (ColorChoice choice : List.of(
                new ColorChoice("Azul", Color.BLUE),
                new ColorChoice("Verde", new Color(0, 128, 0)),
                new ColorChoice("Amarillo", new Color(180, 140, 0)))) {
            JButton button = new JButton(choice.name());
            button.addActionListener(event -> {
                field.setText(choice.name());
                field.setForeground(choice.color());
            });
            body.add(button);
        }

        body.add(field);
        return wrap(body);
    }

    private static String sumGreaterThanTen(String left, String right) {
        BigDecimal sum = new BigDecimal(left).add(new BigDecimal(right));
        return sum.compareTo(BigDecimal.TEN) > 0
                ? "La suma es mayor que 10"
                : "La suma es menor o igual a 10";
    }

    private static String describeSign(String value) {
        int sign = new BigDecimal(value).signum();
        if (sign == 0) {
            return "El número es 0";
        }
        return sign < 0
                ? "El número es negativo"
                : "El número es positivo";
    }

    private static String compareCharacters(String left, String right) {
        if (left.isEmpty() || right.isEmpty()) {
            throw new IllegalArgumentException("Introduzca dos caracteres");
        }

        int comparison = Character.compare(left.charAt(0), right.charAt(0));
        if (comparison == 0) {
            return "Los caracteres son iguales";
        }
        return comparison < 0
                ? "El primer carácter es menor"
                : "El primer carácter es mayor";
    }

    private static String describeInterval(String value) {
        BigDecimal number = new BigDecimal(value);
        boolean inside = number.compareTo(BigDecimal.TEN) >= 0
                && number.compareTo(BigDecimal.valueOf(100)) <= 0;
        return inside
                ? "Está entre 10 y 100"
                : "No está entre 10 y 100";
    }

    private static String selectedLanguage(JRadioButton java, JRadioButton kotlin) {
        if (java.isSelected()) {
            return "Java";
        }
        if (kotlin.isSelected()) {
            return "Kotlin";
        }
        return "Otro";
    }

    private static void addStudent(
            JTextField name,
            DefaultListModel<String> model) {
        String value = name.getText().trim();
        if (value.isEmpty()) {
            error(name, "Debe introducir un nombre");
            return;
        }

        model.addElement(value);
        name.setText("");
    }

    private static void move(
            JList<String> source,
            DefaultListModel<String> from,
            DefaultListModel<String> to) {
        for (String value : source.getSelectedValuesList()) {
            to.addElement(value);
            from.removeElement(value);
        }
    }

    private static JTextField readOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    private static void addRows(JPanel panel, Object... labelComponentPairs) {
        for (int index = 0; index < labelComponentPairs.length; index += 2) {
            panel.add(new JLabel(String.valueOf(labelComponentPairs[index])));
            panel.add((Component) labelComponentPairs[index + 1]);
        }
    }

    private static JPanel wrap(Component body) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    private static void safe(JTextField output, Supplier<String> operation) {
        try {
            output.setText(operation.get());
        } catch (RuntimeException exception) {
            output.setText("");
            error(output, exception.getMessage());
        }
    }

    private static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message == null ? "Entrada no válida" : message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    @FunctionalInterface
    private interface BinaryOperation {
        String apply(String left, String right);
    }

    @FunctionalInterface
    private interface UnaryOperation {
        String apply(String value);
    }

    private record ColorChoice(String name, Color color) {
    }
}
