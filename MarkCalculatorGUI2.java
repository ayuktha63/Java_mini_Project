import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MarkCalculatorGUI2 extends JFrame {
    private JTextField totalRecordsField;
    private JTextField lastDateField;
    private JButton calculateButton;
    private JTextArea resultTextArea;

    public MarkCalculatorGUI2() {
        setTitle("Mark Calculator GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        totalRecordsField = new JTextField(10);
        lastDateField = new JTextField(10);
        calculateButton = new JButton("Calculate");
        resultTextArea = new JTextArea(10, 20);
        resultTextArea.setEditable(false);

        add(new JLabel("Enter the total number of records: "));
        add(totalRecordsField);
        add(new JLabel("Enter the Last Date (YYYY-MM-DD): "));
        add(lastDateField);
        add(calculateButton);
        add(new JLabel("Results: "));
        add(new JScrollPane(resultTextArea));

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMarks();
            }
        });
    }

    private void calculateMarks() {
        try {
            int t_records = Integer.parseInt(totalRecordsField.getText());
            String lastDateString = lastDateField.getText();
            LocalDate lastDate = LocalDate.parse(lastDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            int[] array = new int[t_records];

            for (int i = 0; i < array.length; i++) {
                String opDateString = JOptionPane.showInputDialog("Enter o/p verified Date (YYYY-MM-DD) for Roll No " + (i + 1) + ": ");
                LocalDate opDate = LocalDate.parse(opDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                int mark = calculateMark(lastDate, opDate);
                array[i] = mark;
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                result.append("Roll No: ").append(i + 1).append(" Mark: ").append(array[i]).append("\n");
            }

            resultTextArea.setText(result.toString());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for total records. Please enter a valid number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred. Please check your inputs and try again.");
        }
    }

    private static int calculateMark(LocalDate lastDate, LocalDate opDate) {
        int mark = 10;
        long daysBetween = ChronoUnit.DAYS.between(lastDate, opDate);

        if (daysBetween <= 0) {
            mark = 10;
        } else if (daysBetween <= 7) {
            mark = mark - 1;
        } else if (daysBetween <= 14) {
            mark = mark - 2;
        } else if (daysBetween <= 21) {
            mark = mark - 3;
        } else if (daysBetween <= 28) {
            mark = mark - 4;
        } else {
            mark = 5;
        }

        return mark;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MarkCalculatorGUI2 markCalculatorGUI = new MarkCalculatorGUI2();
                markCalculatorGUI.setSize(400, 300);
                markCalculatorGUI.setVisible(true);
            }
        });
    }
}

