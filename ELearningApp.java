import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ELearningApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("E-Learning - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userField = new JTextField(15);
        passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (authenticate(user, pass)) {
                if (user.equals("admin")) {
                    dispose();
                    showAdminDashboard();
                } else {
                    dispose();
                    new UserDashboard(user);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login eșuat!");
            }
        });

        registerButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (register(user, pass)) {
                JOptionPane.showMessageDialog(this, "Cont creat cu succes!");
            } else {
                JOptionPane.showMessageDialog(this, "Utilizatorul există deja!");
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la autentificare: " + e.getMessage());
        }
        return false;
    }

    private boolean register(String username, String password) {
        if (userExists(username)) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Eroare la scrierea în fișier: " + e.getMessage());
        }
        return false;
    }

    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.split(",")[0].equals(username)) return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private void showAdminDashboard() {
        JFrame adminFrame = new JFrame("E-Learning - Admin Dashboard");
        adminFrame.setSize(500, 400);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setLocationRelativeTo(null);

        JButton editExercises = new JButton("Editează exerciții");
        JButton verifyMaterials = new JButton("Verifică materiale");
        JButton viewFeedback = new JButton("Vezi feedback");
        JButton manageLessons = new JButton("Manager Lecții");
        JButton logout = new JButton("Logout");

        editExercises.addActionListener(e -> new EditExercisesFrame());
        verifyMaterials.addActionListener(e -> new VerifyMaterialsFrame());
        viewFeedback.addActionListener(e -> new ViewFeedbackFrame());
        manageLessons.addActionListener(e -> new ManagerLectiiFrame());
        logout.addActionListener(e -> {
            adminFrame.dispose();
            new LoginFrame();
        });

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.add(editExercises);
        panel.add(verifyMaterials);
        panel.add(viewFeedback);
        panel.add(manageLessons);
        panel.add(logout);

        adminFrame.add(panel);
        adminFrame.setVisible(true);
    }
}

class UserDashboard extends JFrame {
    private String username;

    public UserDashboard(String username) {
        this.username = username;
        setTitle("E-Learning - User Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Bun venit, " + username);
        JButton viewProfile = new JButton("Profil");
        JButton feedback = new JButton("Trimite feedback");
        JButton solveExercises = new JButton("Rezolvă exerciții");
        JButton viewLessons = new JButton("Lecții");
        JButton logout = new JButton("Logout");
        JTextArea lessonArea = new JTextArea("Bine ai venit! Introducere în limba engleză");
        lessonArea.setEditable(false);

        viewProfile.addActionListener(e -> new ProfileFrame(username));
        feedback.addActionListener(e -> new FeedbackFrame(username));
        solveExercises.addActionListener(e -> new SolveExercisesFrame());
        viewLessons.addActionListener(e -> new LectiiFrame());
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        JPanel menuPanel = new JPanel();
        menuPanel.add(viewProfile);
        menuPanel.add(feedback);
        menuPanel.add(solveExercises);
        menuPanel.add(viewLessons);
        menuPanel.add(logout);

        add(welcomeLabel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.SOUTH);
        add(new JScrollPane(lessonArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
