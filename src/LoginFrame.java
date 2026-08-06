import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * LoginFrame.java
 *
 * The first window the user sees. Takes username/password,
 * checks them against AuthManager, and opens MainFrame on success.
 *
 * Only the visuals changed here (modern card on a gradient background,
 * rounded fields/buttons via UITheme) — the login logic underneath is
 * exactly the same as before.
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthManager authManager;

    public LoginFrame(AuthManager authManager) {
        this.authManager = authManager;

        setTitle("CRM.io — Login");
        setSize(420, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setResizable(false);

        setContentPane(new GradientBackground());
        getContentPane().setLayout(new GridBagLayout());

        UITheme.Card card = new UITheme.Card(20);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(36, 34, 30, 34));
        card.setPreferredSize(new Dimension(330, 410));

        // ---- logo / brand ----
        JLabel brand = new JLabel("CRM.io");
        brand.setFont(UITheme.fontBold(24));
        brand.setForeground(UITheme.ACCENT_BLUE);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Welcome back");
        title.setFont(UITheme.fontBold(18));
        title.setForeground(UITheme.TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to access your dashboard");
        subtitle.setFont(UITheme.font(12));
        subtitle.setForeground(UITheme.TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(brand);
        card.add(Box.createVerticalStrut(18));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(26));

        // ---- username ----
        JLabel userLbl = fieldLabel("Username");
        usernameField = new JTextField();
        UITheme.styleField(usernameField);
        sizeField(usernameField);

        // ---- password ----
        JLabel passLbl = fieldLabel("Password");
        passwordField = new JPasswordField();
        UITheme.styleField(passwordField);
        sizeField(passwordField);

        card.add(userLbl);
        card.add(Box.createVerticalStrut(6));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(16));
        card.add(passLbl);
        card.add(Box.createVerticalStrut(6));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(26));

        // ---- login button ----
        UITheme.RoundButton loginButton = new UITheme.RoundButton("Log In", UITheme.ACCENT_BLUE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        card.add(loginButton);

        card.add(Box.createVerticalStrut(14));
        JLabel hint = new JLabel("Default: admin / admin123");
        hint.setFont(UITheme.font(11));
        hint.setForeground(UITheme.TEXT_MUTED);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(hint);

        GridBagConstraints gbc = new GridBagConstraints();
        getContentPane().add(card, gbc);

        // pressing Enter in the password field also triggers login
        passwordField.addActionListener(this::attemptLogin);
        loginButton.addActionListener(this::attemptLogin);
    }

    private JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.fontBold(12));
        label.setForeground(UITheme.TEXT_DARK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private void sizeField(JComponent field) {
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        // fixed width (not MAX_VALUE) so BoxLayout centers it instead of
        // stretching it to fill the card from the left edge
        Dimension size = new Dimension(260, 40);
        field.setMinimumSize(size);
        field.setMaximumSize(size);
        field.setPreferredSize(size);
    }

    private void attemptLogin(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (authManager.login(username, password)) {
            // success — open the main application window
            new MainFrame(authManager).setVisible(true);
            this.dispose(); // close the login window
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    /**
     * Simple diagonal gradient background panel (navy -> blue-purple),
     * just for visual flair behind the login card.
     */
    private static class GradientBackground extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0x14, 0x1B, 0x32),
                    getWidth(), getHeight(), new Color(0x3D, 0x2B, 0x6E));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}