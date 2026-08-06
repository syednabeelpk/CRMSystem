import java.io.Serializable;

/**
 * Lead.java
 * Owner: Ayan
 *
 * Stores one sales lead and its current status in the pipeline.
 * Status is modeled as an enum so it can only ever be one of four valid values.
 */
public class Lead implements Serializable {

    private static final long serialVersionUID = 1L;

    // ---------- status enum ----------
    public enum Status {
        NEW,
        IN_PROGRESS,
        CLOSED_WON,
        CLOSED_LOST
    }

    // ---------- fields ----------
    private String leadID;
    private String leadName;
    private Status status;
    private String customerID; // links this lead back to a Customer

    // ---------- constructor ----------
    public Lead(String leadID, String leadName, Status status, String customerID) {
        this.leadID = leadID;
        this.leadName = leadName;
        this.status = status;
        this.customerID = customerID;
    }

    // ---------- getters ----------
    public String getLeadID() {
        return leadID;
    }

    public String getLeadName() {
        return leadName;
    }

    public Status getStatus() {
        return status;
    }

    public String getCustomerID() {
        return customerID;
    }

    // ---------- setters ----------
    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ---------- helper ----------
    @Override
    public String toString() {
        return "Lead ID: " + leadID +
               " | Name: " + leadName +
               " | Status: " + status +
               " | Linked Customer ID: " + customerID;
    }
}
