import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MainFrame.java
 *
 * The main application window shown after a successful login.
 * Visually this is now a modern sidebar-navigation app (Dashboard /
 * Customers / Leads / Interactions / Reports) instead of the original
 * JTabbedPane, styled with UITheme. Functionally it's the same as
 * before — it reuses CustomerManager, LeadManager, FileManager, and
 * ReportGenerator exactly as the console version did; the GUI is just
 * a different front-end calling the same backend logic.
 */
public class MainFrame extends JFrame {

    private AuthManager authManager;
    private CustomerManager customerManager = new CustomerManager();
    private LeadManager leadManager = new LeadManager();
    private FileManager fileManager = new FileManager();
    private ReportGenerator reportGenerator = new ReportGenerator();
    private ArrayList<Interaction> interactionList = new ArrayList<>();

    // Table models — kept as fields so we can refresh them after add/update/delete
    private DefaultTableModel customerTableModel;
    private JTable customerTable;

    private DefaultTableModel leadTableModel;
    private JTable leadTable;

    private DefaultTableModel interactionTableModel;
    private JTable interactionTable;

    private JTextArea reportArea;

    // ---- navigation ----
    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private Map<String, UITheme.NavButton> navButtons = new LinkedHashMap<>();

    // ---- dashboard stat labels (refreshed each time the dashboard is shown) ----
    private JLabel statCustomersValue;
    private JLabel statLeadsValue;
    private JLabel statClosedValue;

    public MainFrame(AuthManager authManager) {
        this.authManager = authManager;

        // load existing data from file at startup
        customerManager.setCustomerList(fileManager.loadCustomers());
        leadManager.setLeadList(fileManager.loadLeads());

        setTitle("CRM.io — Logged in as " + authManager.getCurrentUserRole());
        setSize(1150, 700);
        setMinimumSize(new Dimension(950, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buildSidebar(), BorderLayout.WEST);
        getContentPane().add(buildMainArea(), BorderLayout.CENTER);

        refreshCustomerTable();
        refreshLeadTable();
        refreshDashboard();
    }

    // ======================================================
    // SIDEBAR
    // ======================================================
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(22, 16, 18, 16));

        JLabel brand = new JLabel("CRM.io");
        brand.setFont(UITheme.fontBold(22));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(28));

        sidebar.add(sectionLabel("MANAGEMENT"));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(navButton("Dashboard", "dashboard"));
        sidebar.add(navButton("Customers", "customers"));
        sidebar.add(navButton("Leads", "leads"));
        sidebar.add(navButton("Interactions", "interactions"));
        sidebar.add(navButton("Reports", "reports"));

        sidebar.add(Box.createVerticalGlue());

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x2A, 0x33, 0x57));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(12));

        JLabel userLbl = new JLabel("Signed in as");
        userLbl.setFont(UITheme.font(11));
        userLbl.setForeground(UITheme.SIDEBAR_TEXT_DIM);
        userLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roleLbl = new JLabel(authManager.getCurrentUserRole());
        roleLbl.setFont(UITheme.fontBold(14));
        roleLbl.setForeground(Color.WHITE);
        roleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(userLbl);
        sidebar.add(Box.createVerticalStrut(2));
        sidebar.add(roleLbl);
        sidebar.add(Box.createVerticalStrut(12));

        UITheme.OutlineButton logoutBtn = new UITheme.OutlineButton("Log Out");
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        logoutBtn.addActionListener(e -> doLogout());
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.fontBold(11));
        label.setForeground(UITheme.SIDEBAR_TEXT_DIM);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        return label;
    }

    private UITheme.NavButton navButton(String label, String cardKey) {
        UITheme.NavButton btn = new UITheme.NavButton(label);
        btn.addActionListener(e -> showCard(cardKey));
        navButtons.put(cardKey, btn);
        return btn;
    }

    private void showCard(String key) {
        cardLayout.show(contentPanel, key);
        for (Map.Entry<String, UITheme.NavButton> entry : navButtons.entrySet()) {
            entry.getValue().setActive(entry.getKey().equals(key));
        }
        if (key.equals("dashboard")) {
            refreshDashboard();
        }
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Log out of the CRM?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            authManager.logout();
            this.dispose();
            new LoginFrame(new AuthManager()).setVisible(true);
        }
    }

    // ======================================================
    // MAIN CONTENT AREA
    // ======================================================
    private JPanel buildMainArea() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UITheme.APP_BG);

        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        contentPanel.add(buildDashboardPanel(), "dashboard");
        contentPanel.add(buildCustomerTab(), "customers");
        contentPanel.add(buildLeadTab(), "leads");
        contentPanel.add(buildInteractionTab(), "interactions");
        contentPanel.add(buildReportTab(), "reports");

        main.add(contentPanel, BorderLayout.CENTER);

        showCard("dashboard");
        return main;
    }

    // ======================================================
    // DASHBOARD
    // ======================================================
    private JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 18));
        panel.setOpaque(false);

        panel.add(UITheme.sectionHeader("Overview", "Welcome back, here's what's happening today."),
                BorderLayout.NORTH);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 18, 0));
        statsRow.setOpaque(false);

        UITheme.Card customersCard = UITheme.statCard("Total Customers", "0", "Live count", UITheme.ACCENT_BLUE);
        UITheme.Card leadsCard = UITheme.statCard("Active Leads", "0", "New + In Progress", UITheme.ACCENT_ORANGE);
        UITheme.Card closedCard = UITheme.statCard("Closed Deals", "0", "Closed Won", UITheme.ACCENT_GREEN);

        statCustomersValue = (JLabel) customersCard.getComponent(1);
        statLeadsValue = (JLabel) leadsCard.getComponent(1);
        statClosedValue = (JLabel) closedCard.getComponent(1);

        statsRow.add(customersCard);
        statsRow.add(leadsCard);
        statsRow.add(closedCard);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(statsRow);
        center.add(Box.createVerticalStrut(18));

        UITheme.Card recentCard = new UITheme.Card();
        recentCard.setLayout(new BorderLayout(0, 10));
        JLabel recentTitle = new JLabel("Recent Customers");
        recentTitle.setFont(UITheme.fontBold(15));
        recentTitle.setForeground(UITheme.TEXT_DARK);
        recentCard.add(recentTitle, BorderLayout.NORTH);

        String[] columns = {"ID", "Name", "Phone", "Email", "Type"};
        DefaultTableModel previewModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable previewTable = new JTable(previewModel);
        UITheme.styleTable(previewTable);
        JScrollPane previewScroll = new JScrollPane(previewTable);
        previewScroll.setBorder(BorderFactory.createEmptyBorder());
        previewScroll.setPreferredSize(new Dimension(0, 220));
        recentCard.add(previewScroll, BorderLayout.CENTER);

        center.add(recentCard);

        // keep a reference so refreshDashboard() can repopulate it
        this.dashboardPreviewModel = previewModel;

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private DefaultTableModel dashboardPreviewModel;

    private void refreshDashboard() {
        int totalCustomers = customerManager.getCustomerList().size();
        int activeLeads = reportGenerator.countActiveLeads(leadManager.getLeadList());
        int closedDeals = reportGenerator.countClosedDeals(leadManager.getLeadList());

        if (statCustomersValue != null) statCustomersValue.setText(String.valueOf(totalCustomers));
        if (statLeadsValue != null) statLeadsValue.setText(String.valueOf(activeLeads));
        if (statClosedValue != null) statClosedValue.setText(String.valueOf(closedDeals));

        if (dashboardPreviewModel != null) {
            dashboardPreviewModel.setRowCount(0);
            ArrayList<Customer> list = customerManager.getCustomerList();
            int limit = Math.min(list.size(), 6);
            for (int i = 0; i < limit; i++) {
                Customer c = list.get(i);
                dashboardPreviewModel.addRow(new Object[]{
                        c.getCustomerID(), c.getName(), c.getPhone(), c.getEmail(), c.getCustomerType()
                });
            }
        }
    }

    // ======================================================
    // CUSTOMER TAB
    // ======================================================
    private JPanel buildCustomerTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);
        panel.add(UITheme.sectionHeader("Customers", "Manage your customer records."), BorderLayout.NORTH);

        UITheme.Card card = new UITheme.Card();
        card.setLayout(new BorderLayout(0, 14));

        String[] columns = {"ID", "Name", "Phone", "Email", "Address", "Type"};
        customerTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // read-only table; edits happen through the dialog
            }
        };
        customerTable = new JTable(customerTableModel);
        UITheme.styleTable(customerTable);
        JScrollPane scroll = new JScrollPane(customerTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);

        UITheme.RoundButton addBtn = new UITheme.RoundButton("Add Customer", UITheme.ACCENT_BLUE);
        addBtn.addActionListener(e -> showAddCustomerDialog());
        buttonPanel.add(addBtn);

        UITheme.OutlineButton updateBtn = new UITheme.OutlineButton("Update Selected");
        updateBtn.addActionListener(e -> showUpdateCustomerDialog());
        buttonPanel.add(updateBtn);

        UITheme.OutlineButton deleteBtn = new UITheme.OutlineButton("Delete Selected");
        deleteBtn.addActionListener(e -> deleteSelectedCustomer());
        buttonPanel.add(deleteBtn);

        JTextField searchField = new JTextField(15);
        UITheme.styleField(searchField);
        UITheme.RoundButton searchBtn = new UITheme.RoundButton("Search", UITheme.ACCENT_PURPLE);
        searchBtn.addActionListener(e -> searchCustomers(searchField.getText().trim()));
        UITheme.OutlineButton clearSearchBtn = new UITheme.OutlineButton("Clear");
        clearSearchBtn.addActionListener(e -> { searchField.setText(""); refreshCustomerTable(); });

        buttonPanel.add(Box.createHorizontalStrut(12));
        buttonPanel.add(searchField);
        buttonPanel.add(searchBtn);
        buttonPanel.add(clearSearchBtn);

        card.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void showAddCustomerDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField typeField = new JTextField();

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("Customer ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Phone:")); form.add(phoneField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Address:")); form.add(addressField);
        form.add(new JLabel("Type (Individual/Business):")); form.add(typeField);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer ID and Name are required.",
                        "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Customer c = new Customer(
                    idField.getText().trim(),
                    nameField.getText().trim(),
                    phoneField.getText().trim(),
                    emailField.getText().trim(),
                    addressField.getText().trim(),
                    typeField.getText().trim()
            );
            customerManager.addCustomer(c);
            fileManager.saveCustomers(customerManager.getCustomerList());
            refreshCustomerTable();
            refreshDashboard();
        }
    }

    private void showUpdateCustomerDialog() {
        int row = customerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer in the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String customerID = (String) customerTableModel.getValueAt(row, 0);

        JTextField phoneField = new JTextField((String) customerTableModel.getValueAt(row, 2));
        JTextField emailField = new JTextField((String) customerTableModel.getValueAt(row, 3));
        JTextField addressField = new JTextField((String) customerTableModel.getValueAt(row, 4));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("New Phone:")); form.add(phoneField);
        form.add(new JLabel("New Email:")); form.add(emailField);
        form.add(new JLabel("New Address:")); form.add(addressField);

        int result = JOptionPane.showConfirmDialog(this, form, "Update Customer (ID: " + customerID + ")",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            customerManager.updateCustomer(customerID, phoneField.getText().trim(),
                    emailField.getText().trim(), addressField.getText().trim());
            fileManager.saveCustomers(customerManager.getCustomerList());
            refreshCustomerTable();
        }
    }

    private void deleteSelectedCustomer() {
        int row = customerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer in the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // TODO (Ali Mehdi / team decision): restrict this to Admin only, e.g.:
        // if (!authManager.isAdmin()) { show error and return; }

        String customerID = (String) customerTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete customer " + customerID + "? This cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            customerManager.deleteCustomer(customerID);
            fileManager.saveCustomers(customerManager.getCustomerList());
            refreshCustomerTable();
            refreshDashboard();
        }
    }

    private void searchCustomers(String keyword) {
        if (keyword.isEmpty()) {
            refreshCustomerTable();
            return;
        }
        ArrayList<Customer> results = customerManager.searchCustomer(keyword);
        customerTableModel.setRowCount(0);
        for (Customer c : results) {
            customerTableModel.addRow(new Object[]{
                    c.getCustomerID(), c.getName(), c.getPhone(),
                    c.getEmail(), c.getAddress(), c.getCustomerType()
            });
        }
    }

    private void refreshCustomerTable() {
        customerTableModel.setRowCount(0);
        for (Customer c : customerManager.getCustomerList()) {
            customerTableModel.addRow(new Object[]{
                    c.getCustomerID(), c.getName(), c.getPhone(),
                    c.getEmail(), c.getAddress(), c.getCustomerType()
            });
        }
    }

    // ======================================================
    // LEAD TAB
    // ======================================================
    private JPanel buildLeadTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);
        panel.add(UITheme.sectionHeader("Leads", "Track your sales pipeline."), BorderLayout.NORTH);

        UITheme.Card card = new UITheme.Card();
        card.setLayout(new BorderLayout(0, 14));

        String[] columns = {"Lead ID", "Lead Name", "Status", "Customer ID"};
        leadTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        leadTable = new JTable(leadTableModel);
        UITheme.styleTable(leadTable);
        JScrollPane scroll = new JScrollPane(leadTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);

        UITheme.RoundButton addBtn = new UITheme.RoundButton("Add Lead", UITheme.ACCENT_BLUE);
        addBtn.addActionListener(e -> showAddLeadDialog());
        buttonPanel.add(addBtn);

        UITheme.OutlineButton statusBtn = new UITheme.OutlineButton("Update Status");
        statusBtn.addActionListener(e -> showUpdateLeadStatusDialog());
        buttonPanel.add(statusBtn);

        buttonPanel.add(Box.createHorizontalStrut(12));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"All", "NEW", "IN_PROGRESS", "CLOSED_WON", "CLOSED_LOST"});
        filterBox.addActionListener(e -> filterLeadsByStatus((String) filterBox.getSelectedItem()));
        buttonPanel.add(new JLabel("Filter by status:"));
        buttonPanel.add(filterBox);

        card.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void showAddLeadDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField custIdField = new JTextField();

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Lead ID:")); form.add(idField);
        form.add(new JLabel("Lead Name:")); form.add(nameField);
        form.add(new JLabel("Linked Customer ID:")); form.add(custIdField);

        int result = JOptionPane.showConfirmDialog(this, form, "Add Lead",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lead ID and Name are required.",
                        "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Lead lead = new Lead(idField.getText().trim(), nameField.getText().trim(),
                    Lead.Status.NEW, custIdField.getText().trim());
            leadManager.addLead(lead);
            fileManager.saveLeads(leadManager.getLeadList());
            refreshLeadTable();
            refreshDashboard();
        }
    }

    private void showUpdateLeadStatusDialog() {
        int row = leadTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a lead in the table first.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String leadID = (String) leadTableModel.getValueAt(row, 0);
        String[] statusOptions = {"NEW", "IN_PROGRESS", "CLOSED_WON", "CLOSED_LOST"};

        String chosen = (String) JOptionPane.showInputDialog(this,
                "New status for lead " + leadID + ":", "Update Lead Status",
                JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);

        if (chosen != null) {
            leadManager.updateStatus(leadID, Lead.Status.valueOf(chosen));
            fileManager.saveLeads(leadManager.getLeadList());
            refreshLeadTable();
            refreshDashboard();
        }
    }

    private void filterLeadsByStatus(String status) {
        if (status == null || status.equals("All")) {
            refreshLeadTable();
            return;
        }
        ArrayList<Lead> results = leadManager.filterByStatus(Lead.Status.valueOf(status));
        leadTableModel.setRowCount(0);
        for (Lead l : results) {
            leadTableModel.addRow(new Object[]{
                    l.getLeadID(), l.getLeadName(), l.getStatus(), l.getCustomerID()
            });
        }
    }

    private void refreshLeadTable() {
        leadTableModel.setRowCount(0);
        for (Lead l : leadManager.getLeadList()) {
            leadTableModel.addRow(new Object[]{
                    l.getLeadID(), l.getLeadName(), l.getStatus(), l.getCustomerID()
            });
        }
    }

    // ======================================================
    // INTERACTION TAB
    // ======================================================
    private JPanel buildInteractionTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);
        panel.add(UITheme.sectionHeader("Interactions", "Log calls, meetings, and notes."), BorderLayout.NORTH);

        UITheme.Card card = new UITheme.Card();
        card.setLayout(new BorderLayout(0, 14));

        String[] columns = {"Customer ID", "Type", "Date", "Notes"};
        interactionTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        interactionTable = new JTable(interactionTableModel);
        UITheme.styleTable(interactionTable);
        JScrollPane scroll = new JScrollPane(interactionTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        UITheme.RoundButton addBtn = new UITheme.RoundButton("Log Interaction", UITheme.ACCENT_BLUE);
        addBtn.addActionListener(e -> showLogInteractionDialog());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addBtn);
        card.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void showLogInteractionDialog() {
        JTextField custIdField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Call", "Meeting", "Note"});
        JTextField dateField = new JTextField();
        JTextArea notesArea = new JTextArea(3, 20);

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Customer ID:")); form.add(custIdField);
        form.add(new JLabel("Type:")); form.add(typeBox);
        form.add(new JLabel("Date (e.g. 2026-06-22):")); form.add(dateField);
        form.add(new JLabel("Notes:")); form.add(new JScrollPane(notesArea));

        int result = JOptionPane.showConfirmDialog(this, form, "Log Interaction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Interaction interaction = new Interaction(
                    "INT-" + System.currentTimeMillis(),
                    custIdField.getText().trim(),
                    (String) typeBox.getSelectedItem(),
                    dateField.getText().trim(),
                    notesArea.getText().trim()
            );
            interactionList.add(interaction);
            // NOTE: interactions aren't saved to file yet — see FileManager TODO
            // to add saveInteractions()/loadInteractions() the same way customers/leads work.
            refreshInteractionTable();
        }
    }

    private void refreshInteractionTable() {
        interactionTableModel.setRowCount(0);
        for (Interaction i : interactionList) {
            interactionTableModel.addRow(new Object[]{
                    i.getCustomerID(), i.getType(), i.getDate(), i.getNotes()
            });
        }
    }

    // ======================================================
    // REPORT TAB
    // ======================================================
    private JPanel buildReportTab() {
        JPanel panel = new JPanel(new BorderLayout(0, 16));
        panel.setOpaque(false);
        panel.add(UITheme.sectionHeader("Reports", "Generate a summary of your CRM data."), BorderLayout.NORTH);

        UITheme.Card card = new UITheme.Card();
        card.setLayout(new BorderLayout(0, 14));

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        reportArea.setForeground(UITheme.TEXT_DARK);
        reportArea.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(reportArea);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        UITheme.RoundButton generateBtn = new UITheme.RoundButton("Generate Summary Report", UITheme.ACCENT_BLUE);
        generateBtn.addActionListener(e -> generateReport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(generateBtn);
        card.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(card, BorderLayout.CENTER);
        return panel;
    }

    private void generateReport() {
        int totalCustomers = customerManager.getCustomerList().size();
        int activeLeads = reportGenerator.countActiveLeads(leadManager.getLeadList());
        int closedDeals = reportGenerator.countClosedDeals(leadManager.getLeadList());

        StringBuilder sb = new StringBuilder();
        sb.append("===== CRM Summary Report =====\n");
        sb.append("Total Customers : ").append(totalCustomers).append("\n");
        sb.append("Active Leads     : ").append(activeLeads).append("\n");
        sb.append("Closed Deals     : ").append(closedDeals).append("\n");
        sb.append("===============================\n");

        reportArea.setText(sb.toString());
    }
}
