import java.util.ArrayList;

/**
 * LeadManager.java
 * Owner: Ayan
 *
 * Holds the list of all leads and manages the sales pipeline:
 * adding new leads, updating their status, and filtering by status.
 */
public class LeadManager {

    private ArrayList<Lead> leadList;

    public LeadManager() {
        this.leadList = new ArrayList<>();
    }

    public void setLeadList(ArrayList<Lead> loadedList) {
        this.leadList = loadedList;
    }

    public ArrayList<Lead> getLeadList() {
        return leadList;
    }

    /**
     * Adds a new lead to the pipeline.
     */
    public void addLead(Lead lead) {
        leadList.add(lead);
        System.out.println("Lead added successfully: " + lead.getLeadName());
    }

    /**
     * Updates the status of an existing lead by ID.
     * Returns true if found and updated, false if not found.
     */
    public boolean updateStatus(String leadID, Lead.Status newStatus) {
        for (Lead l : leadList) {
            if (l.getLeadID().equals(leadID)) {
                l.setStatus(newStatus);
                System.out.println("Lead status updated: " + l.getLeadName() + " -> " + newStatus);
                return true;
            }
        }
        System.out.println("Lead not found with ID: " + leadID);
        return false;
    }

    /**
     * Returns all leads matching a given status.
     */
    public ArrayList<Lead> filterByStatus(Lead.Status status) {
        ArrayList<Lead> results = new ArrayList<>();
        for (Lead l : leadList) {
            if (l.getStatus() == status) {
                results.add(l);
            }
        }
        return results;
    }

    /**
     * Returns all leads linked to a specific customer.
     * TODO: useful if you want to show lead history on a customer's profile screen.
     */
    public ArrayList<Lead> getLeadsByCustomer(String customerID) {
        ArrayList<Lead> results = new ArrayList<>();
        for (Lead l : leadList) {
            if (l.getCustomerID().equals(customerID)) {
                results.add(l);
            }
        }
        return results;
    }

    /**
     * Prints all leads to the console — useful for quick testing.
     */
    public void printAllLeads() {
        if (leadList.isEmpty()) {
            System.out.println("No leads found.");
            return;
        }
        for (Lead l : leadList) {
            System.out.println(l);
        }
    }
}
