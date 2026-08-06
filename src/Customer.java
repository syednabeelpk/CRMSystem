import java.io.Serializable;

/**
 * Customer.java
 * Owner: Nabeel
 *
 * Stores all the basic information for one customer.
 * This class only holds data + getters/setters — no logic here.
 * Implements Serializable so it can be saved to a .dat file by FileManager.
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    // ---------- fields ----------
    private String customerID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String customerType; // e.g. "Individual", "Business"

    // ---------- constructor ----------
    public Customer(String customerID, String name, String phone, String email, String address, String customerType) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.customerType = customerType;
    }

    // ---------- getters ----------
    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCustomerType() {
        return customerType;
    }

    // ---------- setters ----------
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    // ---------- helper ----------
    // Used when printing customer details to the console / report
    @Override
    public String toString() {
        return "ID: " + customerID +
               " | Name: " + name +
               " | Phone: " + phone +
               " | Email: " + email +
               " | Address: " + address +
               " | Type: " + customerType;
    }
}
