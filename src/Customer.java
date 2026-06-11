
public class Customer {
    int Id;
    String name;
    int phoneNumber;
    double amount;
    String paymentCaptured;
    String label;
    String sideNote;
    double totalAmountCaptured;
    int dateDay;
    int dateMonth;
    int dateYear;
    int totalRecord; //test commit
    

    
    // 1. Default / Empty Constructor (Creates a completely blank record)
    public Customer() {
    }

    // 2. Minimal Constructor (Just the ID - Matches your Main example)
    public Customer(int Id) {
        this(Id, "", 0, 0.0, "", "", "", 0.0, 0, 0, 0, 0);
    }

    // 3. Lead Creation Constructor (ID, Name, and Phone only)
    public Customer(int Id, String name, int phoneNumber) {
        this(Id, name, phoneNumber, 0.0, "Pending", "Lead", "", 0.0, 0, 0, 0, 0);
    }

    // 4. Financial Transaction Constructor (ID, Name, Amount, Label, and Date)
    public Customer(int Id, String name, double amount, String label, int dateDay, int dateMonth, int dateYear) {
        this(Id, name, 0, amount, "No", label, "", 0.0, dateDay, dateMonth, dateYear, 0);
    }

    // 5. Your Original All-Arguments Constructor (Master Constructor)
    public Customer(int Id, String name, int phoneNumber, double amount, String paymentCaptured, String label, String sideNote, double totalAmountCaptured, int dateDay, int dateMonth, int dateYear, int totalRecord) {
        this.Id = Id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.paymentCaptured = paymentCaptured;
        this.label = label;
        this.sideNote = sideNote;
        this.totalAmountCaptured = totalAmountCaptured;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.totalRecord = totalRecord;
    }
    

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentCaptured() {
        return paymentCaptured;
    }

    public String getLabel() {
        return label;
    }

    public String getSideNote() {
        return sideNote;
    }

    public double getTotalAmountCaptured() {
        return totalAmountCaptured;
    }

    public int getDateDay() {
        return dateDay;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public int getDateYear() {
        return dateYear;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
    public int getId(){
        return this.Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentCaptured(String paymentCaptured) {
        this.paymentCaptured = paymentCaptured;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSideNote(String sideNote) {
        this.sideNote = sideNote;
    }

    public void setTotalAmountCaptured(double totalAmountCaptured) {
        this.totalAmountCaptured = totalAmountCaptured;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    @Override
    public String toString() {
        return "Records{" + "Id=" + Id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", amount=" + amount + ", paymentCaptured=" + paymentCaptured + ", label=" + label + ", sideNote=" + sideNote + ", totalAmountCaptured=" + totalAmountCaptured + ", dateDay=" + dateDay + ", dateMonth=" + dateMonth + ", dateYear=" + dateYear + ", totalRecord=" + totalRecord + '}';
    }
    
    
    
    
    
    
    
    
}
