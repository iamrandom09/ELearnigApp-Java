import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class SolveExercisesFrame extends JFrame {
    private int totalScore = 0;
    private List<ExerciseManager.Exercise> exercises;
    private final List<String> order = List.of("usor", "mediu", "greu");
    private int levelIndex = 0;
    private Deque<ExerciseManager.Exercise> queue = new ArrayDeque<>();

    private JLabel levelLabel;
    private JTextArea questionArea;
    private JTextField answerField;
    private JButton nextButton;

    public SolveExercisesFrame() {
        setTitle("Rezolvare Exerciții");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        exercises = ExerciseManager.loadExercises();

        Map<String, List<ExerciseManager.Exercise>> byLevel = new HashMap<>();
        for (String lvl : order) byLevel.put(lvl, new ArrayList<>());
        for (ExerciseManager.Exercise ex : exercises)
            byLevel.getOrDefault(ex.level.toLowerCase(), new ArrayList<>()).add(ex);

        queue.addAll(byLevel.get(order.get(levelIndex)));

        levelLabel = new JLabel("Nivel: ");
        questionArea = new JTextArea(3, 40);
        questionArea.setEditable(false);
        answerField = new JTextField(30);
        nextButton = new JButton("Trimite răspuns");

        nextButton.addActionListener(e -> checkAnswer());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(levelLabel);
        topPanel.add(new JScrollPane(questionArea));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Răspunsul tău:"));
        inputPanel.add(answerField);

        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);

        if (!queue.isEmpty()) {
            showExercise();
        } else {
            JOptionPane.showMessageDialog(this, "Nu există exerciții disponibile.");
            dispose();
            return;
        }

        setVisible(true);
    }

    private void showExercise() {
        if (queue.isEmpty()) {
            levelIndex++;
            if (levelIndex >= order.size()) {
                JOptionPane.showMessageDialog(this,
                        "✔ Felicitări! Ai terminat toate nivelele.\nScor total: " + totalScore + " puncte");
                dispose();
                return;
            }

            String nextLevel = order.get(levelIndex);
            List<ExerciseManager.Exercise> nextExercises = new ArrayList<>();
            for (ExerciseManager.Exercise ex : exercises) {
                if (ex.level.equalsIgnoreCase(nextLevel)) {
                    nextExercises.add(ex);
                }
            }

            queue.addAll(nextExercises);
            JOptionPane.showMessageDialog(this, "Treci la nivelul: " + nextLevel.toUpperCase());
        }

        ExerciseManager.Exercise ex = queue.peek();
        levelLabel.setText("Nivel: " + ex.level);
        questionArea.setText(ex.question);
        answerField.setText("");
    }

    private void checkAnswer() {
        ExerciseManager.Exercise ex = queue.poll();
        String userAnswer = answerField.getText().trim();
        if (userAnswer.equalsIgnoreCase(ex.answer)) {
            totalScore += ex.points;
            JOptionPane.showMessageDialog(this, "✔ Răspuns corect! +" + ex.points + " puncte");
        } else {
            JOptionPane.showMessageDialog(this,
                    "✘ Răspuns greșit!\nRăspuns corect: " + ex.answer + "\nVei relua exercițiul la finalul nivelului.");
            queue.addLast(ex);
        }
        showExercise();
    }
}