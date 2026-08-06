import java.io.*;
import java.util.ArrayList;

/**
 * FileManager.java
 * Owner: Ali Mehdi
 *
 * Handles saving and loading Customer and Lead data to local .dat files
 * using Java's built-in object serialization. This is what makes data
 * persist between runs of the application.
 *
 * TODO: wrap calls to these methods in try/catch where they're used in Main,
 * or extend these methods to handle errors however your team prefers.
 */
public class FileManager {

    private static final String CUSTOMER_FILE = "data/customers.dat";
    private static final String LEAD_FILE = "data/leads.dat";

    /**
     * Saves the customer list to disk. Overwrites the file each time.
     */
    public void saveCustomers(ArrayList<Customer> customerList) {
        try {
            new File("data").mkdirs(); // make sure the data/ folder exists
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CUSTOMER_FILE));
            out.writeObject(customerList);
            out.close();
            System.out.println("Customer data saved.");
        } catch (IOException e) {
            System.out.println("Error saving customer data: " + e.getMessage());
        }
    }

    /**
     * Loads the customer list from disk.
     * Returns an empty list if the file doesn't exist yet (first run).
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Customer> loadCustomers() {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Customer> list = (ArrayList<Customer>) in.readObject();
            in.close();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves the lead list to disk. Overwrites the file each time.
     */
    public void saveLeads(ArrayList<Lead> leadList) {
        try {
            new File("data").mkdirs();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(LEAD_FILE));
            out.writeObject(leadList);
            out.close();
            System.out.println("Lead data saved.");
        } catch (IOException e) {
            System.out.println("Error saving lead data: " + e.getMessage());
        }
    }

    /**
     * Loads the lead list from disk.
     * Returns an empty list if the file doesn't exist yet (first run).
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Lead> loadLeads() {
        File file = new File(LEAD_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Lead> list = (ArrayList<Lead>) in.readObject();
            in.close();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading lead data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
