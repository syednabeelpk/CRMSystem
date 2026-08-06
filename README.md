# CRM.io — Customer Relationship Management System

A desktop CRM (Customer Relationship Management) application built in **Java** with a modern **Java Swing** GUI. Developed as a semester project.

---

## 🖥️ Features

- **Login System** — Role-based authentication (Admin / SalesRep) with hardcoded credentials
- **Dashboard** — Live stats: Total Customers, Active Leads, and Closed Deals with a recent customers preview table
- **Customer Management** — Add, update, delete, and search customers (by name, phone, or email)
- **Lead Management** — Add leads, update their pipeline status, and filter by status
- **Interaction Logging** — Log calls, meetings, and notes tied to customers
- **Reports** — Generate a CRM summary report in-app
- **Data Persistence** — Customer and lead data saved to local `.dat` files using Java serialization

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher installed
- NetBeans IDE (recommended) **or** any Java IDE

### Option 1 — NetBeans
1. Open NetBeans → `File` → `Open Project`
2. Select the `CustomerRelationshipManagementSystem` folder
3. Right-click the project → **Clean and Build**
4. Right-click `MainGUI.java` → **Run File**

### Option 2 — Run the JAR
```bash
java -jar dist/CustomerRelationshipManagementSystem.jar
```

> ⚠️ Make sure you run from the project root so the `data/` folder (for persistence) is created in the right place.

---

## 🔐 Default Login Credentials

| Username | Password | Role     |
|----------|----------|----------|
| admin    | admin123 | Admin    |
| sales    | sales123 | SalesRep |

---

## 📁 Project Structure

```
src/
├── MainGUI.java          # Entry point — launches the Swing GUI
├── Main.java             # Console-mode entry point (kept for reference)
├── LoginFrame.java       # Login window with gradient background
├── MainFrame.java        # Main app window (sidebar + 5 panels)
├── UITheme.java          # Shared design system (colors, fonts, components)
├── AuthManager.java      # Login & role checking
├── Customer.java         # Customer data model
├── CustomerManager.java  # Customer CRUD logic
├── Lead.java             # Lead data model with Status enum
├── LeadManager.java      # Lead pipeline logic
├── Interaction.java      # Interaction data model
├── ReportGenerator.java  # Summary report logic
└── FileManager.java      # Save/load data to disk (.dat files)

data/
├── customers.dat         # Persisted customer data (auto-created on first run)
└── leads.dat             # Persisted lead data (auto-created on first run)
```

---

## 👥 Team

| Module                  | Owner     |
|-------------------------|-----------|
| AuthManager             | Ali Mehdi |
| FileManager             | Ali Mehdi |
| ReportGenerator         | Ali Mehdi |
| Customer / Interaction  | Nabeel    |
| CustomerManager         | Taimoor   |
| Lead / LeadManager      | Ayan      |
| Main / UI               | Nabeel    |

---

## ⚠️ Known Limitations

- Credentials are hardcoded (not stored in a file)
- Interactions are not persisted to disk between sessions
- No role-based access control enforced in the UI yet

---

## 🛠️ Tech Stack

- **Language:** Java (Standard Edition)
- **GUI:** Java Swing
- **Build Tool:** Apache Ant (NetBeans `build.xml`)
- **Persistence:** Java Object Serialization (`.dat` files)
- **IDE:** Apache NetBeans
