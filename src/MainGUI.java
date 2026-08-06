import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * MainGUI.java
 *
 * Entry point for the Swing version of the CRM application.
 * Run THIS class (not Main.java) to launch the GUI.
 *
 * Main.java is left in the project as the original console version —
 * keep it if you still want to demo the console flow, or delete it
 * once everyone's happy with the GUI.
 */
public class MainGUI {

    public static void main(String[] args) {
        // Swing apps should be started on the "Event Dispatch Thread" (EDT).
        // SwingUtilities.invokeLater just makes sure that happens correctly.
        SwingUtilities.invokeLater(() -> {

            // Use the cross-platform "Metal" look and feel as a clean base —
            // our custom UITheme components (rounded cards/buttons/sidebar)
            // draw themselves on top of this, so the OS-native look doesn't
            // fight with the modern styling.
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                // if it fails for any reason, just fall back to the default L&F
                System.out.println("Could not set look and feel: " + e.getMessage());
            }

            AuthManager authManager = new AuthManager();
            LoginFrame loginFrame = new LoginFrame(authManager);
            loginFrame.setVisible(true);
        });
    }
}
