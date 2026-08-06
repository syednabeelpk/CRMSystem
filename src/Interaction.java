import java.io.Serializable;

/**
 * Interaction.java
 * Owner: Nabeel
 *
 * Stores one logged interaction (a call, meeting, or note) tied to a customer.
 * A customer can have many Interaction objects — that history is kept in
 * an ArrayList<Interaction> inside Main (or you can add an InteractionManager
 * later if you want to follow the same pattern as Customer/Lead).
 */
public class Interaction implements Serializable {

    private static final long serialVersionUID = 1L;

    // ---------- fields ----------
    private String interactionID;
    private String customerID;   // links back to the Customer this belongs to
    private String type;         // "Call", "Meeting", or "Note"
    private String date;         // TODO: store as String for simplicity, e.g. "2026-06-22"
    private String notes;

    // ---------- constructor ----------
    public Interaction(String interactionID, String customerID, String type, String date, String notes) {
        this.interactionID = interactionID;
        this.customerID = customerID;
        this.type = type;
        this.date = date;
        this.notes = notes;
    }

    // ---------- getters ----------
    public String getInteractionID() {
        return interactionID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    // ---------- setters ----------
    public void setNotes(String notes) {
        this.notes = notes;
    }

    // ---------- helper ----------
    @Override
    public String toString() {
        return "[" + date + "] " + type + " — " + notes + " (Customer ID: " + customerID + ")";
    }
}
