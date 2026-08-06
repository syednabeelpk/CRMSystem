import java.util.ArrayList;

/**
 * CustomerManager.java
 * Owner: Taimoor
 *
 * Holds the list of all customers and provides the core CRUD operations:
 * add, update, delete, search. This is the biggest logic class in the project.
 *
 * TODO markers show exactly what you need to fill in.
 */
public class CustomerManager {

    private ArrayList<Customer> customerList;

    public CustomerManager() {
        this.customerList = new ArrayList<>();
    }

    // Lets Main / FileManager load an existing list back in after reading from file
    public void setCustomerList(ArrayList<Customer> loadedList) {
        this.customerList = loadedList;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Adds a new customer to the list.
     */
    public void addCustomer(Customer customer) {
        // TODO: optionally check that customerID doesn't already exist before adding
        customerList.add(customer);
        System.out.println("Customer added successfully: " + customer.getName());
    }

    /**
     * Updates an existing customer's details by ID.
     * Returns true if found and updated, false if not found.
     */
    public boolean updateCustomer(String customerID, String newPhone, String newEmail, String newAddress) {
        for (Customer c : customerList) {
            if (c.getCustomerID().equals(customerID)) {
                // TODO: only update fields that were actually passed in (not null/blank)
                c.setPhone(newPhone);
                c.setEmail(newEmail);
                c.setAddress(newAddress);
                System.out.println("Customer updated: " + c.getName());
                return true;
            }
        }
        System.out.println("Customer not found with ID: " + customerID);
        return false;
    }

    /**
     * Deletes a customer by ID.
     * Returns true if found and removed, false if not found.
     */
    public boolean deleteCustomer(String customerID) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getCustomerID().equals(customerID)) {
                Customer removed = customerList.remove(i);
                System.out.println("Customer deleted: " + removed.getName());
                return true;
            }
        }
        System.out.println("Customer not found with ID: " + customerID);
        return false;
    }

    /**
     * Searches customers by name, phone, or email (partial match, case-insensitive).
     * TODO: extend this with more specific search-by-field methods if you want.
     */
    public ArrayList<Customer> searchCustomer(String keyword) {
        ArrayList<Customer> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Customer c : customerList) {
            if (c.getName().toLowerCase().contains(lowerKeyword) ||
                c.getPhone().contains(keyword) ||
                c.getEmail().toLowerCase().contains(lowerKeyword)) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Filters customers by their customer type (e.g. "Business" / "Individual").
     */
    public ArrayList<Customer> filterByType(String type) {
        ArrayList<Customer> results = new ArrayList<>();
        for (Customer c : customerList) {
            if (c.getCustomerType().equalsIgnoreCase(type)) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Prints all customers to the console — useful for quick testing.
     */
    public void printAllCustomers() {
        if (customerList.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer c : customerList) {
            System.out.println(c);
        }
    }
}
