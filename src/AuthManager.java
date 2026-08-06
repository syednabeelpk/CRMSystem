import java.util.HashMap;

/**
 * AuthManager.java
 * Owner: Ali Mehdi
 *
 * Handles login and role checking. For this project, users are hardcoded
 * in a HashMap rather than stored in a database — that's intentional and
 * fine for a semester project.
 *
 * TODO: if you want, replace the hardcoded map with values loaded from a
 * simple users.txt file using FileManager, so credentials aren't in the code.
 */
public class AuthManager {

    // username -> password
    private HashMap<String, String> users;
    // username -> role ("Admin" or "SalesRep")
    private HashMap<String, String> roles;

    private String loggedInUser = null;

    public AuthManager() {
        users = new HashMap<>();
        roles = new HashMap<>();

        // TODO: change these to your own test credentials
        users.put("admin", "admin123");
        roles.put("admin", "Admin");

        users.put("sales", "sales123");
        roles.put("sales", "SalesRep");
    }

    /**
     * Checks username/password against the stored users.
     * Returns true if valid, false otherwise.
     */
    public boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            loggedInUser = username;
            System.out.println("Login successful. Welcome, " + username + " (" + getRole(username) + ")");
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    /**
     * Returns the role of a given username, or null if the username doesn't exist.
     */
    public String getRole(String username) {
        return roles.get(username);
    }

    /**
     * Convenience method: returns the role of whoever is currently logged in.
     */
    public String getCurrentUserRole() {
        if (loggedInUser == null) return null;
        return getRole(loggedInUser);
    }

    /**
     * Convenience method: checks if the currently logged-in user is an Admin.
     * Use this in Main to block Admin-only actions like deleteCustomer().
     */
    public boolean isAdmin() {
        return "Admin".equals(getCurrentUserRole());
    }

    public void logout() {
        loggedInUser = null;
    }
}
