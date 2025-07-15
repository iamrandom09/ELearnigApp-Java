import javax.swing.*;
import java.io.*;

public class LectiiFrame extends JFrame {
    public LectiiFrame() {
        setTitle("Lecții disponibile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("lectii.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                area.append("- " + line + "\n");
            }
        } catch (IOException e) {
            area.setText("Nu există lecții sau nu s-au putut încărca.");
        }

        add(new JScrollPane(area));
        setVisible(true);
    }
}