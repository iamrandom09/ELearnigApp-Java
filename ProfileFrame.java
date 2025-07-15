import javax.swing.*;

public class ProfileFrame extends JFrame {
    public ProfileFrame(String username) {
        setTitle("Profil Utilizator");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea("Profilul utilizatorului: " + username);
        area.setEditable(false);
        add(area);

        setVisible(true);
    }
}