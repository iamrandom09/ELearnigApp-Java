import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class EditExercisesFrame extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> exerciseList;
    private java.util.List<ExerciseManager.Exercise> exercises;

    public EditExercisesFrame() {
        setTitle("Editare Exerciții");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exercises = ExerciseManager.loadExercises();
        listModel = new DefaultListModel<>();
        for (ExerciseManager.Exercise ex : exercises) {
            listModel.addElement(formatExercise(ex));
        }

        exerciseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(exerciseList);

        JButton addBtn = new JButton("Adaugă");
        JButton deleteBtn = new JButton("Șterge");

        addBtn.addActionListener(e -> addExercise());
        deleteBtn.addActionListener(e -> deleteExercise());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String formatExercise(ExerciseManager.Exercise ex) {
        return "[" + ex.level + "] " + ex.question + " → " + ex.answer + " (" + ex.points + "p)";
    }

    private void addExercise() {
        JTextField levelField = new JTextField();
        JTextField questionField = new JTextField();
        JTextField answerField = new JTextField();
        JTextField pointsField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nivel:"));
        panel.add(levelField);
        panel.add(new JLabel("Întrebare:"));
        panel.add(questionField);
        panel.add(new JLabel("Răspuns:"));
        panel.add(answerField);
        panel.add(new JLabel("Punctaj:"));
        panel.add(pointsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adaugă Exercițiu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String level = levelField.getText();
                String question = questionField.getText();
                String answer = answerField.getText();
                int points = Integer.parseInt(pointsField.getText());

                ExerciseManager.Exercise newEx = new ExerciseManager.Exercise(level, question, answer, points);
                exercises.add(newEx);
                listModel.addElement(formatExercise(newEx));
                ExerciseManager.saveExercises(exercises);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Punctajul trebuie să fie un număr.");
            }
        }
    }

    private void deleteExercise() {
        int index = exerciseList.getSelectedIndex();
        if (index >= 0) {
            exercises.remove(index);
            listModel.remove(index);
            ExerciseManager.saveExercises(exercises);
        } else {
            JOptionPane.showMessageDialog(this, "Selectați un exercițiu de șters.");
        }
    }
}