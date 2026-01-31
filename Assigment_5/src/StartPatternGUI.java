import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPatternGUI {
	public static void main(String[] args) {
        // Create main window
        JFrame frame = new JFrame("Star Pattern Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Top panel for input
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter number of rows:"));
        JTextField inputField = new JTextField(5);
        topPanel.add(inputField);
        JButton generateButton = new JButton("Generate");
        topPanel.add(generateButton);

        // Text area for output
        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button logic
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText(""); // clear previous output
                try {
                    int rows = Integer.parseInt(inputField.getText().trim());
                    if (rows <= 0) {
                        outputArea.setText("Please enter a positive number!");
                        return;
                    }

                    // Pattern (a)
                    outputArea.append("(a)\n");
                    for (int i = 1; i <= rows; i++) {
                        for (int j = 1; j <= i; j++) {
                            outputArea.append("*");
                        }
                        outputArea.append("\n");
                    }
                    outputArea.append("\n");

                    // Pattern (b)
                    outputArea.append("(b)\n");
                    for (int i = rows; i >= 1; i--) {
                        for (int j = 1; j <= i; j++) {
                            outputArea.append("*");
                        }
                        outputArea.append("\n");
                    }
                    outputArea.append("\n");

                    // Pattern (c)
                    outputArea.append("(c)\n");
                    for (int i = rows; i >= 1; i--) {
                        for (int j = 1; j <= rows - i; j++) {
                            outputArea.append(" ");
                        }
                        for (int j = 1; j <= i; j++) {
                            outputArea.append("*");
                        }
                        outputArea.append("\n");
                    }
                    outputArea.append("\n");

                    // Pattern (d)
                    outputArea.append("(d)\n");
                    for (int i = 1; i <= rows; i++) {
                        for (int j = 1; j <= rows - i; j++) {
                            outputArea.append(" ");
                        }
                        for (int j = 1; j <= i; j++) {
                            outputArea.append("*");
                        }
                        outputArea.append("\n");
                    }

                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid input! Please enter a positive integer.");
                }
            }
        });

        frame.setVisible(true);
    }
}
