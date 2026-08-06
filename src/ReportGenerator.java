import java.util.ArrayList;

/**
 * ReportGenerator.java
 * Owner: Ali Mehdi
 *
 * Computes and displays a simple summary report: total customers,
 * active leads, and closed deals.
 */
public class ReportGenerator {

    /**
     * Prints a summary report to the console using the current customer and lead lists.
     */
    public void generateSummary(ArrayList<Customer> customerList, ArrayList<Lead> leadList) {
        int totalCustomers = customerList.size();
        int activeLeads = countActiveLeads(leadList);
        int closedDeals = countClosedDeals(leadList);

        System.out.println("===== CRM Summary Report =====");
        System.out.println("Total Customers : " + totalCustomers);
        System.out.println("Active Leads     : " + activeLeads);
        System.out.println("Closed Deals     : " + closedDeals);
        System.out.println("===============================");
    }

    /**
     * Counts leads that are either NEW or IN_PROGRESS.
     */
    public int countActiveLeads(ArrayList<Lead> leadList) {
        int count = 0;
        for (Lead l : leadList) {
            if (l.getStatus() == Lead.Status.NEW || l.getStatus() == Lead.Status.IN_PROGRESS) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts leads that are CLOSED_WON.
     * TODO: decide if you also want to count CLOSED_LOST separately in the report.
     */
    public int countClosedDeals(ArrayList<Lead> leadList) {
        int count = 0;
        for (Lead l : leadList) {
            if (l.getStatus() == Lead.Status.CLOSED_WON) {
                count++;
            }
        }
        return count;
    }
}
