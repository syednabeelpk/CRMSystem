import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main.java
 * Owner: Nabeel
 *
 * Entry point of the application. Shows the menu, takes user input,
 * and calls the appropriate manager classes.
 *
 * This file is already wired up to compile and run end-to-end with
 * placeholder logic — fill in the TODOs in the other classes, and this
 * menu will exercise all of them.
 */
public class Main {

    static Scanner scanner = new Scanner(System.in);

    static AuthManager authManager = new AuthManager();
    static CustomerManager customerManager = new CustomerManager();
    static LeadManager leadManager = new LeadManager();
    static ReportGenerator reportGenerator = new ReportGenerator();
    static FileManager fileManager = new FileManager();

    public static void main(String[] args) {

        // ----- load existing data from file at startup -----
        customerManager.setCustomerList(fileManager.loadCustomers());
        leadManager.setLeadList(fileManager.loadLeads());

        // ----- login loop -----
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("\n=== CRM Login ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            loggedIn = authManager.login(username, password);
        }

        // ----- main menu loop -----
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    customerMenu();
                    break;
                case "2":
                    leadMenu();
                    break;
                case "3":
                    logInteraction();
                    break;
                case "4":
                    reportGenerator.generateSummary(customerManager.getCustomerList(), leadManager.getLeadList());
                    break;
                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }

        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n=== CRM Main Menu ===");
        System.out.println("1. Customer Management");
        System.out.println("2. Lead Management");
        System.out.println("3. Log Interaction");
        System.out.println("4. View Summary Report");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // ---------------- Customer Management ----------------
    static void customerMenu() {
        System.out.println("\n-- Customer Management --");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. Search Customer");
        System.out.println("5. View All Customers");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Customer ID: ");
                String id = scanner.nextLine();
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Phone: ");
                String phone = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Address: ");
                String address = scanner.nextLine();
                System.out.print("Customer Type: ");
                String type = scanner.nextLine();

                Customer newCustomer = new Customer(id, name, phone, email, address, type);
                customerManager.addCustomer(newCustomer);
                fileManager.saveCustomers(customerManager.getCustomerList());
                break;

            case "2":
                System.out.print("Enter Customer ID to update: ");
                String updateId = scanner.nextLine();
                System.out.print("New Phone: ");
                String newPhone = scanner.nextLine();
                System.out.print("New Email: ");
                String newEmail = scanner.nextLine();
                System.out.print("New Address: ");
                String newAddress = scanner.nextLine();
                customerManager.updateCustomer(updateId, newPhone, newEmail, newAddress);
                fileManager.saveCustomers(customerManager.getCustomerList());
                break;

            case "3":
                System.out.print("Enter Customer ID to delete: ");
                String deleteId = scanner.nextLine();
                customerManager.deleteCustomer(deleteId);
                fileManager.saveCustomers(customerManager.getCustomerList());
                break;

            case "4":
                System.out.print("Enter search keyword (name/phone/email): ");
                String keyword = scanner.nextLine();
                ArrayList<Customer> results = customerManager.searchCustomer(keyword);
                if (results.isEmpty()) {
                    System.out.println("No matches found.");
                } else {
                    for (Customer c : results) System.out.println(c);
                }
                break;

            case "5":
                customerManager.printAllCustomers();
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // ---------------- Lead Management ----------------
    static void leadMenu() {
        System.out.println("\n-- Lead Management --");
        System.out.println("1. Add Lead");
        System.out.println("2. Update Lead Status");
        System.out.println("3. View All Leads");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Lead ID: ");
                String leadId = scanner.nextLine();
                System.out.print("Lead Name: ");
                String leadName = scanner.nextLine();
                System.out.print("Linked Customer ID: ");
                String custId = scanner.nextLine();

                Lead newLead = new Lead(leadId, leadName, Lead.Status.NEW, custId);
                leadManager.addLead(newLead);
                fileManager.saveLeads(leadManager.getLeadList());
                break;

            case "2":
                System.out.print("Enter Lead ID to update: ");
                String updateLeadId = scanner.nextLine();
                System.out.println("New Status: 1=NEW 2=IN_PROGRESS 3=CLOSED_WON 4=CLOSED_LOST");
                String statusChoice = scanner.nextLine();

                Lead.Status newStatus;
                switch (statusChoice) {
                    case "1": newStatus = Lead.Status.NEW; break;
                    case "2": newStatus = Lead.Status.IN_PROGRESS; break;
                    case "3": newStatus = Lead.Status.CLOSED_WON; break;
                    case "4": newStatus = Lead.Status.CLOSED_LOST; break;
                    default:
                        System.out.println("Invalid status choice.");
                        return;
                }
                leadManager.updateStatus(updateLeadId, newStatus);
                fileManager.saveLeads(leadManager.getLeadList());
                break;

            case "3":
                leadManager.printAllLeads();
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // ---------------- Interaction Logging ----------------
    // TODO: this currently just prints — add an ArrayList<Interaction> here
    // (or build an InteractionManager like the others) if you want these saved/persisted.
    static void logInteraction() {
        System.out.println("\n-- Log Interaction --");
        System.out.print("Customer ID: ");
        String custId = scanner.nextLine();
        System.out.print("Type (Call/Meeting/Note): ");
        String type = scanner.nextLine();
        System.out.print("Date (e.g. 2026-06-22): ");
        String date = scanner.nextLine();
        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        Interaction interaction = new Interaction("INT-" + System.currentTimeMillis(), custId, type, date, notes);
        System.out.println("Interaction logged: " + interaction);
        // TODO: store this in a list and persist it via FileManager, similar to customers/leads
    }
}
