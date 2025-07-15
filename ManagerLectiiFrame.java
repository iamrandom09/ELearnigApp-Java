import javax.swing.*;
import java.io.*;

public class ManagerLectiiFrame extends JFrame {
    public ManagerLectiiFrame() {
        setTitle("Adaugă Lecții");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea inputArea = new JTextArea(5, 30);
        JButton saveButton = new JButton("Salvează lecția");

        saveButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("lectii.txt", true))) {
                writer.write(inputArea.getText());
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Lecție adăugată!");
                inputArea.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Eroare la salvare.");
            }
        });

        add(new JScrollPane(inputArea), "Center");
        add(saveButton, "South");

        setVisible(true);
    }
}
