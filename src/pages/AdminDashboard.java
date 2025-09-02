package pages;

import dao.BroadcastDao;
import dao.EmployeeDao;
import models.Broadcast;
import models.Employee;
import utils.BroadcastQueue;
import utils.EmployeeList;
import utils.UIelements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class AdminDashboard extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel employeePanel;
    private JPanel broadcastPanel;
    private JScrollPane employeeScrollPane;
    private JScrollPane broadcastScrollPane;

    private JTextField employeeNameField;
    private JTextField employeeUsernameField;
    private JPasswordField employeePasswordField;
    private JButton addEmployeeButton;
    private JTextField removeEmployeeUsernameField;
    private JButton removeEmployeeButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JButton viewEmployeesButton;
    private JTextArea broadcastMessageArea;
    private JButton sendBroadcastButton;
    private JTable broadcastTable;
    private DefaultTableModel broadcastTableModel;

    private int companyId;

    private static final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public AdminDashboard(int companyId) {
        this.companyId = companyId;

        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();

        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(PRIMARY_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));

        String[] menuItems = {"Manage Employees", "Broadcast"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            styleButton(menuButton);
            menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
            menuButton.addActionListener(this);
            sidebarPanel.add(menuButton);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        sidebarPanel.add(Box.createVerticalGlue());
        JButton LogoutButton = new JButton("Logout");
        LogoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        LogoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        LogoutButton.addActionListener(this);
        styleButton(LogoutButton);
        sidebarPanel.add(LogoutButton);

        // Content Panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(SECONDARY_COLOR);

        // Employee Panel
        employeePanel = new JPanel(new BorderLayout(10, 10));
        employeePanel.setBackground(SECONDARY_COLOR);

        JPanel addEmployeePanel = createAddEmployeePanel();
        JPanel removeEmployeePanel = createRemoveEmployeePanel();
        JPanel viewEmployeesPanel = createViewEmployeesPanel();

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 2));
        upperPanel.add(addEmployeePanel);
        upperPanel.add(removeEmployeePanel);

        employeePanel.add(upperPanel,BorderLayout.CENTER);
        employeePanel.add(viewEmployeesPanel, BorderLayout.SOUTH);

        employeeScrollPane = new JScrollPane(employeePanel);
        employeeScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Broadcast Panel
        broadcastPanel = createBroadcastPanel();
        broadcastScrollPane = new JScrollPane(broadcastPanel);
        broadcastScrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(employeeScrollPane, "Manage Employees");
        contentPanel.add(broadcastScrollPane, "Broadcast");
    }

    private void layoutComponents() {
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel createAddEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            "Add New Employee",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            HEADER_FONT,
            PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        employeeNameField = new JTextField(20);
        employeeUsernameField = new JTextField(20);
        employeePasswordField = new JPasswordField(20);
        // addEmployeeButton = new JButton("Add Employee");

        panel.add(UIelements.createLabeledField("Employee Name:    ", employeeNameField), gbc);
        panel.add(UIelements.createLabeledField("Employee Username:", employeeUsernameField), gbc);
        panel.add(UIelements.createLabeledField("Employee Password:", employeePasswordField), gbc);

        addEmployeeButton = UIelements.createStyledButton("Add Employee");
        // styleButton(addEmployeeButton);
        addEmployeeButton.addActionListener(this);
        panel.add(addEmployeeButton, gbc);

        return panel;
    }

    private JPanel createRemoveEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            "Remove Employee",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            HEADER_FONT,
            PRIMARY_COLOR
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        removeEmployeeUsernameField = new JTextField(20);
        // removeEmployeeButton = new JButton("Remove Employee");

        panel.add(UIelements.createLabeledField("Employee Username:", removeEmployeeUsernameField), gbc);

        removeEmployeeButton = UIelements.createStyledButton("Remove Employee");
        removeEmployeeButton.addActionListener(this);
        panel.add(removeEmployeeButton, gbc);

        return panel;
    }

    private JPanel createViewEmployeesPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            "View Employees",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            HEADER_FONT,
            PRIMARY_COLOR
        ));

        String[] columnNames = {"Employee ID", "Name", "Username"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(TEXT_FONT);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        // viewEmployeesButton = new JButton("Refresh Employee List");
        // styleButton(viewEmployeesButton);
        viewEmployeesButton = UIelements.createStyledButton("Refresh Employee List");
        viewEmployeesButton.addActionListener(this);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(viewEmployeesButton, BorderLayout.SOUTH);

        handleViewEmployees();

        return panel;
    }

    private JPanel createBroadcastPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            "Broadcast Message",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            HEADER_FONT,
            PRIMARY_COLOR
        ));

        broadcastMessageArea = new JTextArea(5, 30);
        broadcastMessageArea.setLineWrap(true);
        broadcastMessageArea.setWrapStyleWord(true);
        broadcastMessageArea.setFont(TEXT_FONT);
        JScrollPane messageScrollPane = new JScrollPane(broadcastMessageArea);

        // sendBroadcastButton = new JButton("Send Broadcast");
        // styleButton(sendBroadcastButton);
        sendBroadcastButton = UIelements.createStyledButton("Send Broadcast");
        sendBroadcastButton.addActionListener(this);

        String[] columnNames = {"Message", "Timestamp"};
        broadcastTableModel = new DefaultTableModel(columnNames, 0);
        broadcastTable = new JTable(broadcastTableModel);
        broadcastTable.setFont(TEXT_FONT);
        JScrollPane broadcastScrollPane = new JScrollPane(broadcastTable);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(SECONDARY_COLOR);
        inputPanel.add(messageScrollPane, BorderLayout.CENTER);
        inputPanel.add(sendBroadcastButton, BorderLayout.SOUTH);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(broadcastScrollPane, BorderLayout.CENTER);

        updateBroadcastTable();

        return panel;
    }

    /**
     * Creates a labeled text field
     * @param labelText The label for the text field
     * @param textField The JTextField to be labeled
     * @return JPanel containing the labeled text field
     */
    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(SECONDARY_COLOR);
        JLabel label = new JLabel(labelText);
        label.setFont(TEXT_FONT);
        panel.add(label, BorderLayout.WEST);
        textField.setFont(TEXT_FONT);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Add Style to the buttons
     * @param button The JButton to be styled
     */
    private void styleButton(JButton button) {
        button.setFont(TEXT_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        switch (command) {
            case "Manage Employees":
            case "Broadcast":
                cl.show(contentPanel, command);
                break;
            case "Add Employee":
                handleAddEmployee();
                break;
            case "Remove Employee":
                handleRemoveEmployee();
                break;
            case "Refresh Employee List":
                handleViewEmployees();
                break;
            case "Send Broadcast":
                handleSendBroadcast();
                break;
            case "Logout":
                this.dispose();
                new homePage();
                break;
        }
    }

    /**
     * Handles the process of adding a new employee
     */
    private void handleAddEmployee() {
        String name = employeeNameField.getText();
        String username = employeeUsernameField.getText();
        String password = new String(employeePasswordField.getPassword());

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee employee = new Employee(0, companyId, name, username, password);
        EmployeeDao employeeDao = new EmployeeDao();
        boolean success = employeeDao.addEmployee(employee);

        if (success) {
            JOptionPane.showMessageDialog(this, "Employee added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearAddEmployeeFields();
            handleViewEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Clears the fields in the add employee form after an employee is added
     */
    private void clearAddEmployeeFields() {
        employeeNameField.setText("");
        employeeUsernameField.setText("");
        employeePasswordField.setText("");
    }

    /*
     * Handles the process of removing an employee
     */
    private void handleRemoveEmployee() {
        String username = removeEmployeeUsernameField.getText();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the employee username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.getEmployeeByUsername(username);

        if (employee != null && employee.getCompanyId() == companyId) {
            boolean success = employeeDao.deleteEmployee(employee.getEmployeeId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Employee removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                removeEmployeeUsernameField.setText("");
                handleViewEmployees();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to remove employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Employee not found or does not belong to this company.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Refreshes the view of employees in the table
     */
    private void handleViewEmployees() {
        EmployeeDao employeeDao = new EmployeeDao();
        EmployeeList employees = employeeDao.getEmployeesByCompanyId(companyId);

        tableModel.setRowCount(0);

        for (Employee employee : employees) {
            Object[] row = {employee.getEmployeeId(), employee.getEmployeeName(), employee.getEmployeeUsername()};
            tableModel.addRow(row);
        }
    }

    /*
     * Handles the process of sending a broadcast message
     */
    private void handleSendBroadcast() {
        String message = broadcastMessageArea.getText();

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a message to broadcast.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Broadcast broadcast = new Broadcast(0, companyId, message, new Timestamp(System.currentTimeMillis()));
        BroadcastDao broadcastDao = new BroadcastDao();
        boolean success = broadcastDao.addBroadcast(broadcast);

        if (success) {
            JOptionPane.showMessageDialog(this, "Broadcast message sent successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            broadcastMessageArea.setText("");
            updateBroadcastTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send broadcast message.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * Updates the table of broadcast messages
     */
    private void updateBroadcastTable() {
        BroadcastDao broadcastDao = new BroadcastDao();
        BroadcastQueue broadcasts = broadcastDao.getBroadcastsByCompanyId(companyId);

        broadcastTableModel.setRowCount(0);

        for (Broadcast broadcast : broadcasts) {
            Object[] row = {broadcast.getMessage(), broadcast.getTimestamp()};
            broadcastTableModel.addRow(row);
        }
    }
}