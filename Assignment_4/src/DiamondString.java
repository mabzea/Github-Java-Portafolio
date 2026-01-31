import javax.swing.*;
import java.awt.*;

public class DiamondString {
	public static void main(String[] args) {
        // Always run Swing GUI stuff on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            String input = JOptionPane.showInputDialog(null, "Enter a string:", "Diamond Input", JOptionPane.QUESTION_MESSAGE);
            if (input == null) { // user cancelled
                return;
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a non-empty string.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int n = input.length();

            // Use a monospaced text area so spaces line up exactly
            JTextArea textArea = new JTextArea();
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            textArea.setEditable(false);

            // Build top half (including the middle line)
            for (int i = 0; i < n; i++) {
                // leading spaces: n - i - 1
                for (int s = 0; s < n - i - 1; s++) {
                    textArea.append(" ");
                }

                // left part: characters 0..i
                for (int j = 0; j <= i; j++) {
                    textArea.append(String.valueOf(input.charAt(j)));
                }

                // right part: characters i-1..0 (reverse of 0..i-1)
                for (int j = i - 1; j >= 0; j--) {
                    textArea.append(String.valueOf(input.charAt(j)));
                }

                textArea.append("\n");
            }

            // Build bottom half (mirror), excluding middle line
            for (int i = n - 2; i >= 0; i--) {
                for (int s = 0; s < n - i - 1; s++) {
                    textArea.append(" ");
                }
                for (int j = 0; j <= i; j++) {
                    textArea.append(String.valueOf(input.charAt(j)));
                }
                for (int j = i - 1; j >= 0; j--) {
                    textArea.append(String.valueOf(input.charAt(j)));
                }
                textArea.append("\n");
            }

            // Put text area inside a scroll pane and show it in a dialog
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new Dimension(520, 360));
            JOptionPane.showMessageDialog(null, scroll, "Diamond for \"" + input + "\"", JOptionPane.PLAIN_MESSAGE);
        });
    }
}
