package dev.rodrigosambade.swing;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Supplier;
public final class App {
    private App() {
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Boletín 2 — Swing");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JTabbedPane tabs = new JTabbedPane();
            tabs.add("01 Calculadora", calculator());
            tabs.add("02 Absoluto", unary("Valor absoluto", v -> new BigDecimal(v).abs().toPlainString()));
            tabs.add("03 Factorial", unary("Factorial", v -> ExerciseLogic.factorial(Integer.parseInt(v)).toString()));
            tabs.add("04 Potencia", binary("Potencia", (a,b) -> String.valueOf(Math.pow(Double.parseDouble(a), Double.parseDouble(b)))));
            tabs.add("05 Cadena", unary("Longitud", v -> "La cadena tiene " + v.length() + " caracteres."));
            tabs.add("06 Suma > 10", binary("Suma > 10", (a,b) -> new BigDecimal(a).add(new BigDecimal(b)).compareTo(BigDecimal.TEN) > 0 ? "La suma es mayor que 10" : "La suma es menor o igual a 10"));
            tabs.add("07 Signo", unary("Signo", v -> {
                int s = new BigDecimal(v).signum();
                return s == 0 ? "El número es 0" : s < 0 ? "El número es negativo" : "El número es positivo";
            }
            ));
            tabs.add("08 Caracteres", binary("Comparar caracteres", (a,b) -> {
                if (a.isEmpty() || b.isEmpty()) throw new IllegalArgumentException("Introduzca dos caracteres");
                int c = Character.compare(a.charAt(0), b.charAt(0));
                return c == 0 ? "Los caracteres son iguales" : c < 0 ? "El primer carácter es menor" : "El primer carácter es mayor";
            }
            ));
            tabs.add("09 Intervalo", unary("¿Entre 10 y 100?", v -> {
                BigDecimal n = new BigDecimal(v);
                return n.compareTo(BigDecimal.TEN) >= 0 && n.compareTo(BigDecimal.valueOf(100)) <= 0 ? "Está entre 10 y 100" : "No está entre 10 y 100";
            }
            ));
            tabs.add("10 Potencias 2", outputOnly("Potencias de 2", () -> ExerciseLogic.powersOfTwo().toString()));
            tabs.add("11 Múltiplos 4", unary("Múltiplos de 4", v -> ExerciseLogic.multiplesOfFour(Integer.parseInt(v)).toString()));
            tabs.add("12 Saludo", greeting());
            tabs.add("13 Encuesta", survey());
            tabs.add("14 Lista", studentList(false));
            tabs.add("15 Dos listas", studentList(true));
            tabs.add("16 Color", colorSelector());
            frame.add(tabs);
            frame.setSize(760, 520);
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        }
        );
    }
    @FunctionalInterface private interface Binary {
        String apply(String a, String b);
    }
    @FunctionalInterface private interface Unary {
        String apply(String value);
    }
    private static JPanel calculator() {
        JPanel p = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField a = new JTextField(), b = new JTextField(), result = new JTextField();
        result.setEditable(false);
        JComboBox<String> op = new JComboBox<>(new String[]{
            "+","-","*","/"
        }
        );
        JButton calc = new JButton("Calcular"), clear = new JButton("Borrar");
        calc.addActionListener(e -> safe(result, () -> ExerciseLogic.calculate(new BigDecimal(a.getText().trim()), new BigDecimal(b.getText().trim()), String.valueOf(op.getSelectedItem())).stripTrailingZeros().toPlainString()));
        clear.addActionListener(e -> {
            a.setText("");
            b.setText("");
            result.setText("");
            op.setSelectedIndex(0);
        }
        );
        addRows(p, "Número 1", a, "Operación", op, "Número 2", b, "Resultado", result);
        p.add(calc);
        p.add(clear);
        return wrap(p);
    }
    private static JPanel unary(String title, Unary operation) {
        JPanel p = new JPanel(new GridLayout(0,2,8,8));
        JTextField in = new JTextField(), out = new JTextField();
        out.setEditable(false);
        JButton run = new JButton("Calcular");
        run.addActionListener(e -> safe(out, () -> operation.apply(in.getText())));
        addRows(p, title, in, "Resultado", out);
        p.add(run);
        p.add(new JLabel());
        return wrap(p);
    }
    private static JPanel binary(String title, Binary operation) {
        JPanel p = new JPanel(new GridLayout(0,2,8,8));
        JTextField a = new JTextField(), b = new JTextField(), out = new JTextField();
        out.setEditable(false);
        JButton run = new JButton("Calcular");
        run.addActionListener(e -> safe(out, () -> operation.apply(a.getText(), b.getText())));
        addRows(p, title + " — valor 1", a, "Valor 2", b, "Resultado", out);
        p.add(run);
        p.add(new JLabel());
        return wrap(p);
    }
    private static JPanel outputOnly(String title, Supplier<String> operation) {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JTextArea out = new JTextArea();
        out.setEditable(false);
        JButton b = new JButton("Visualizar");
        b.addActionListener(e -> out.setText(operation.get()));
        p.add(b, BorderLayout.NORTH);
        p.add(new JScrollPane(out));
        return wrap(p);
    }
    private static JPanel greeting() {
        JPanel p = new JPanel(new FlowLayout());
        JTextField name = new JTextField(20);
        JButton show = new JButton("Mostrar");
        show.addActionListener(e -> {
            String n = name.getText().trim();
            if (n.isEmpty()) error(p, "Debe introducir un nombre");
            else JOptionPane.showMessageDialog(p, "Hola " + n);
        }
        );
        p.add(new JLabel("Nombre:"));
        p.add(name);
        p.add(show);
        return wrap(p);
    }
    private static JPanel survey() {
        JPanel p = new JPanel(new GridLayout(0,1,6,6));
        JTextField name = new JTextField();
        ButtonGroup group = new ButtonGroup();
        JRadioButton java = new JRadioButton("Java", true), kotlin = new JRadioButton("Kotlin"), other = new JRadioButton("Otro");
        for (JRadioButton b : Arrays.asList(java,kotlin,other)) {
            group.add(b);
            p.add(b);
        }
        JButton result = new JButton("Resultado");
        result.addActionListener(e -> JOptionPane.showMessageDialog(p, "Nombre: " + name.getText().trim() + "\nOpción: " + (java.isSelected()?"Java":kotlin.isSelected()?"Kotlin":"Otro")));
        p.add(new JLabel("Nombre:"));
        p.add(name);
        p.add(result);
        return wrap(p);
    }
    private static JPanel studentList(boolean twoLists) {
        DefaultListModel<String> leftModel = new DefaultListModel<>(), rightModel = new DefaultListModel<>();
        JList<String> left = new JList<>(leftModel), right = new JList<>(rightModel);
        JTextField name = new JTextField(14);
        JButton add = new JButton("Añadir"), remove = new JButton("Borrar");
        add.addActionListener(e -> {
            String n=name.getText().trim();
            if(n.isEmpty()) error(null,"Debe introducir un nombre");
            else {
                leftModel.addElement(n);
                name.setText("");
            }
        }
        );
        remove.addActionListener(e -> left.getSelectedValuesList().forEach(leftModel::removeElement));
        JPanel controls = new JPanel(new FlowLayout());
        controls.add(new JLabel("Nombre"));
        controls.add(name);
        controls.add(add);
        controls.add(remove);
        JPanel p = new JPanel(new BorderLayout(8,8));
        p.add(controls, BorderLayout.NORTH);
        if (!twoLists) p.add(new JScrollPane(left));
        else {
            JButton toRight=new JButton(">>"), toLeft=new JButton("<<");
            toRight.addActionListener(e -> move(left,leftModel,rightModel));
            toLeft.addActionListener(e -> move(right,rightModel,leftModel));
            JPanel center=new JPanel(new GridLayout(1,3,8,8));
            center.add(new JScrollPane(left));
            JPanel arrows=new JPanel(new GridLayout(2,1));
            arrows.add(toRight);
            arrows.add(toLeft);
            center.add(arrows);
            center.add(new JScrollPane(right));
            p.add(center);
        }
        return wrap(p);
    }
    private static void move(JList<String> source, DefaultListModel<String> from, DefaultListModel<String> to) {
        for(String s:source.getSelectedValuesList()) {
            to.addElement(s);
            from.removeElement(s);
        }
    }
    private static JPanel colorSelector() {
        JPanel p = new JPanel(new FlowLayout());
        JTextField field = new JTextField(12);
        field.setEditable(false);
        for (var entry : new Object[][]{
            {
                "Azul", Color.BLUE
            }
            ,{
                "Verde", new Color(0,128,0)
            }
            ,{
                "Amarillo", new Color(180,140,0)
            }
        }
        ) {
            JButton b=new JButton((String)entry[0]);
            Color c=(Color)entry[1];
            b.addActionListener(e->{
                field.setText((String)entry[0]);
                field.setForeground(c);
            }
            );
            p.add(b);
        }
        p.add(field);
        return wrap(p);
    }
    private static void addRows(JPanel p, Object... pairs) {
        for (int i=0;
        i<pairs.length;
        i+=2) {
            p.add(new JLabel(String.valueOf(pairs[i])));
            p.add((Component)pairs[i+1]);
        }
    }
    private static JPanel wrap(JPanel body) {
        JPanel p=new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        p.add(body);
        return p;
    }
    private static void safe(JTextField out, Supplier<String> f) {
        try {
            out.setText(f.get());
        } catch (RuntimeException ex) {
            out.setText("");
            error(out, ex.getMessage());
        }
    }
    private static void error(Component c, String message) {
        JOptionPane.showMessageDialog(c, message == null ? "Entrada no válida" : message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
