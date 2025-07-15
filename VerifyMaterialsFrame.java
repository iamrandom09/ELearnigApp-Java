import javax.swing.*;
import java.io.*;
import java.util.*;

public class VerifyMaterialsFrame extends JFrame {
    public VerifyMaterialsFrame() {
        setTitle("Verificare Materiale");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        List<ExerciseManager.Exercise> list = ExerciseManager.loadExercises();
        for (ExerciseManager.Exercise ex : list) {
            area.append("[" + ex.level + "] " + ex.question + " → Răspuns: " + ex.answer + " (" + ex.points + " puncte)\n");
        }

        add(new JScrollPane(area));
        setVisible(true);
    }
}