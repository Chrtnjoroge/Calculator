import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Calculator implements ActionListener {

    JFrame frame;
    JTextField text;
    JButton[] numberButtons = new JButton[10];
    JButton[] operationButtons = new JButton[15];
    JButton addButton, subButton, mulButton, divButton;
    JButton equButton, decButton, powButton, sqrButton, pecButton;
    JButton delButton, clrButton, onButton, offButton, negButton;
    JPanel panel, equPanel, topPanel;

    Font myFont = new Font("Ubuntu", Font.BOLD, 25);

    ArrayList<Double> numbers = new ArrayList<>();
    char operator;

    Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 700);
        frame.setLayout(null);

        topPanel = new JPanel();
        topPanel.setBounds(50, 25, 400, 50);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.add(topPanel);

        onButton = new JButton("ON");
        onButton.addActionListener(this);
        onButton.setFont(myFont);
        onButton.setFocusable(false);
        topPanel.add(onButton);

        offButton = new JButton("OFF");
        offButton.addActionListener(this);
        offButton.setFont(myFont);
        offButton.setFocusable(false);
        topPanel.add(offButton);

        text = new JTextField();
        text.setBounds(50, 100, 400, 50);
        text.setFont(myFont);
        text.setEditable(false);
        frame.add(text);

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        equButton = new JButton("=");
        decButton = new JButton(".");
        powButton = new JButton("^");
        sqrButton = new JButton("√");
        pecButton = new JButton("%");
        delButton = new JButton("DEL");
        clrButton = new JButton("C");

        operationButtons[0] = addButton;
        operationButtons[1] = subButton;
        operationButtons[2] = mulButton;
        operationButtons[3] = divButton;
        operationButtons[4] = equButton;
        operationButtons[5] = decButton;
        operationButtons[6] = powButton;
        operationButtons[7] = sqrButton;
        operationButtons[8] = pecButton;
        operationButtons[9] = delButton;
        operationButtons[10] = clrButton;

        // Set background color for operation buttons
        for (int i = 0; i < 11; i++) {
            operationButtons[i].addActionListener(this);
            operationButtons[i].setFont(myFont);
            operationButtons[i].setFocusable(false);
            operationButtons[i].setBackground(Color.GRAY); // Set the background color
        }

        panel = new JPanel();
        panel.setBounds(50, 160, 400, 430);
        panel.setLayout(new GridLayout(6, 4, 10, 10));

        // Create functions for number buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(Color.CYAN); // Set the background color
            panel.add(numberButtons[i]);
        }

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(divButton);
        panel.add(powButton);
        panel.add(sqrButton);
        panel.add(pecButton);
        panel.add(clrButton);
        panel.add(delButton);

        equPanel = new JPanel();
        equPanel.setBounds(50, 600, 400, 50);
        equPanel.setLayout(new GridLayout(1, 1));
        equButton.addActionListener(this);
        equButton.setFont(myFont);
        equButton.setFocusable(false);
        equButton.setBackground(Color.LIGHT_GRAY); // Set the background color
        equPanel.add(equButton);

        frame.add(panel);
        frame.add(equPanel);

        frame.setVisible(true);

        onButton.addActionListener(this);
        offButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                text.setText(text.getText().concat(numberButtons[i].getText()));
                break;
            }
        }

        if (e.getSource() == delButton) {
            String currentText = text.getText();
            if (currentText.length() > 0) {
                text.setText(currentText.substring(0, currentText.length() - 1));
            }
        }

        if (e.getSource() == clrButton) {
            text.setText("");
        }

        double result;
        if (e.getSource() == equButton) {
            String equation = text.getText();
            String[] tokens = equation.split("[+\\-*/^√%]");
            numbers.clear();

            for (String token : tokens) {
                numbers.add(Double.parseDouble(token));
            }

            result = calculateResult(numbers, equation);
            text.setText(Double.toString(result));
            numbers.clear();
            numbers.add(result);
        }

        if (e.getSource() == sqrButton) {
            numbers.clear();
            numbers.add(Double.parseDouble(text.getText()));
            result = Math.sqrt(numbers.get(0));
            text.setText(Double.toString(result));
            numbers.clear();
            numbers.add(result);
        }

        if (e.getSource() == pecButton) {
            numbers.clear();
            numbers.add(Double.parseDouble(text.getText()));
            result = numbers.get(0) / 100.0;
            text.setText(Double.toString(result));
            numbers.clear();
            numbers.add(result);
        }

        if (e.getSource() == decButton) {
            String currentText = text.getText();
            if (!currentText.endsWith(".")) {
                String[] tokens = currentText.split("[+\\-*/^√%]");
                String lastToken = tokens[tokens.length - 1];
                if (!lastToken.contains(".")) {
                    text.setText(currentText.concat("."));
                }
            }
        }

        JButton[] operatorButtons = {addButton, subButton, mulButton, divButton, sqrButton, powButton, pecButton};
        for (JButton button : operatorButtons) {
            if (e.getSource() == button) {
                text.setText(text.getText().concat(button.getText()));
                break;
            }
        }

        if (e.getSource() == onButton) {
            text.setEnabled(true);
            for (JButton button : numberButtons) {
                button.setEnabled(true);
            }
            for (JButton button : operationButtons) {
                button.setEnabled(true);
            }
        }

        if (e.getSource() == offButton) {
            text.setEnabled(false);
            for (JButton button : numberButtons) {
                button.setEnabled(false);
            }
            for (JButton button : operationButtons) {
                button.setEnabled(false);
            }
        }
    }

    private double calculateResult(ArrayList<Double> numbers, String equation) {
        double result = numbers.get(0);
        int index = 1;

        for (char op : equation.toCharArray()) {
            if (op == '+' || op == '-' || op == '*' || op == '/' || op == '^') {
                double nextNum = numbers.get(index);
                index++;

                switch (op) {
                    case '+':
                        result += nextNum;
                        break;
                    case '-':
                        result -= nextNum;
                        break;
                    case '*':
                        result *= nextNum;
                        break;
                    case '/':
                        result /= nextNum;
                        break;
                    case '^':
                        result = Math.pow(result, nextNum);
                        break;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
        });
    }
}
