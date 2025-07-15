import javax.swing.*;
import java.io.*;

public class ViewFeedbackFrame extends JFrame {
    public ViewFeedbackFrame() {
        setTitle("Vizualizare Feedback");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea feedbackArea = new JTextArea();
        feedbackArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                feedbackArea.append(line + "\n");
            }
        } catch (IOException e) {
            feedbackArea.setText("Nu s-a putut citi feedback-ul.");
        }

        add(new JScrollPane(feedbackArea));
        setVisible(true);
    }
}