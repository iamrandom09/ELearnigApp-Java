import javax.swing.*;
import java.io.*;

public class FeedbackFrame extends JFrame {
    public FeedbackFrame(String username) {
        setTitle("Trimite Feedback");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea feedbackArea = new JTextArea();
        JButton sendButton = new JButton("Trimite");

        sendButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
                writer.write(username + ": " + feedbackArea.getText());
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Feedback trimis!");
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Eroare la salvarea feedback-ului.");
            }
        });

        add(new JScrollPane(feedbackArea), "Center");
        add(sendButton, "South");

        setVisible(true);
    }
}