package pages;

import dao.CompanyDao;
import dao.EmployeeDao;
import models.Company;
import models.Employee;
import utils.UIelements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homePage extends JFrame implements ActionListener {

    // Components for Login
    private JTextField loginUserId;
    private JPasswordField loginPassword;
    private JButton loginAdminButton;
    private JButton loginEmployeeButton;

    // Components for Registration
    private JTextField registerCompanyName;
    private JTextField registerAdminUserId;
    private JPasswordField registerAdminPassword;
    private JButton registerCompanyButton;

    // Tabbedpane
    private JTabbedPane tabbedPane;

    // Base Colors
    private static final Color PRIMARY_COLOR = new Color(50, 150, 200);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color PANEL_COLOR = new Color(255, 255, 255);
    private static final Color BUTTON_COLOR = new Color(30, 130, 200);
    private static final Color BUTTON_HOVER_COLOR = new Color(20, 100, 180);

    // Font Styles
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 18);

    // Data Access Objects
    private CompanyDao companyDao;
    private EmployeeDao employeeDao;

    public homePage() {
        // Initialize DAOs
        companyDao = new CompanyDao();
        employeeDao = new EmployeeDao();

        // Set Frame Properties
        setTitle("Workflow - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Create and add logo panel
        JPanel logoPanel = createLogoPanel();
        add(logoPanel, BorderLayout.WEST);

        // Create and add tabbed pane for login and registration
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(LABEL_FONT);
        tabbedPane.setForeground(PRIMARY_COLOR);
        tabbedPane.setBackground(PANEL_COLOR);

        // Create and add Login panel
        JPanel loginPanel = createLoginPanel();
        tabbedPane.addTab("Login", loginPanel);

        // Create and add Registeration panel
        JPanel registerPanel = createRegisterPanel();
        tabbedPane.addTab("Register", registerPanel);

        // Center Panel for Tabs
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(tabbedPane, gbc);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Panel To Display Logo and tagline
    /*
     * params: none
     * returns: JPanel
     */
    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(PRIMARY_COLOR);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.add(Box.createVerticalStrut(200));

        JLabel logoLabel = new JLabel("WORKFLOW", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 80));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        JLabel taglineLabel = new JLabel("Streamline Your Company's Communication", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        taglineLabel.setForeground(Color.WHITE);
        logoPanel.add(taglineLabel);

        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return logoPanel;
    }

    // Panel To Hold The Textfields For Login Purpose
    /*
     * params: none
     * returns: JPanel
     */
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(PANEL_COLOR);
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(userIdLabel, gbc);

        loginUserId = new JTextField(25);
        loginUserId.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(loginUserId, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(passwordLabel, gbc);

        loginPassword = new JPasswordField(25);
        loginPassword.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(loginPassword, gbc);

        // loginAdminButton = new JButton("Login as Admin");
        loginAdminButton = UIelements.createStyledButton("Login as Admin");
        loginAdminButton.setPreferredSize(new Dimension(loginPanel.getWidth(),40));
        loginAdminButton.addActionListener(this);
        // styleButton(loginAdminButton);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginAdminButton, gbc);

        // loginEmployeeButton = new JButton("Login as Employee");
        loginEmployeeButton = UIelements.createStyledButton("Login as Employee");
        loginEmployeeButton.setPreferredSize(new Dimension(loginPanel.getWidth(),40));
        loginEmployeeButton.addActionListener(this);
        // styleButton(loginEmployeeButton);
        gbc.gridy = 3;
        loginPanel.add(loginEmployeeButton, gbc);

        return loginPanel;
    }

    // Panel To Hold The Textfields For Registeration Purpose
    /*
     * params: none
     * returns: JPanel
     */
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(PANEL_COLOR);
        registerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel companyNameLabel = new JLabel("Company Name:");
        companyNameLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        registerPanel.add(companyNameLabel, gbc);

        registerCompanyName = new JTextField(25);
        registerCompanyName.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        registerPanel.add(registerCompanyName, gbc);

        JLabel adminUserIdLabel = new JLabel("Admin User ID:");
        adminUserIdLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        registerPanel.add(adminUserIdLabel, gbc);

        registerAdminUserId = new JTextField(25);
        registerAdminUserId.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        registerPanel.add(registerAdminUserId, gbc);

        JLabel adminPasswordLabel = new JLabel("Admin Password:");
        adminPasswordLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        registerPanel.add(adminPasswordLabel, gbc);

        registerAdminPassword = new JPasswordField(25);
        registerAdminPassword.setFont(TEXT_FONT);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        registerPanel.add(registerAdminPassword, gbc);

        // registerCompanyButton = new JButton("Register Company");
        registerCompanyButton = UIelements.createStyledButton("Register Company");
        registerCompanyButton.addActionListener(this);
        // styleButton(registerCompanyButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(registerCompanyButton, gbc);

        return registerPanel;
    }

    // Method to change the style of buttons
    /*
     * params: JButton
     * returns: void
     */
    private void styleButton(JButton button) {
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setPreferredSize(new Dimension(250, 50));
        button.setOpaque(true);
        button.setBorderPainted(true);

        // mouse listener to change the button color on hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    // Overridden method to checck the actions performed on the page
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginAdminButton) {
            handleAdminLogin();
        } else if (e.getSource() == loginEmployeeButton) {
            handleEmployeeLogin();
        } else if (e.getSource() == registerCompanyButton) {
            handleRegisterCompany();
        }
    }

    // Method to check the credentials when an admin logins
    private void handleAdminLogin() {
        String adminUserName = loginUserId.getText();
        String password = new String(loginPassword.getPassword());

        if (!adminUserName.isEmpty() && !password.isEmpty()) {

            // Check if the admin exists in database
            Company company = companyDao.getCompanyByAdminUserId(adminUserName);

            // Check if the password matches with the one stored in database
            if (company != null && company.getAdminPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Logged In", "Success", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                new AdminDashboard(company.getCompanyId());
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to check the credentials when an employee logins
    private void handleEmployeeLogin() {
        String userId = loginUserId.getText();
        String password = new String(loginPassword.getPassword());

        if (!userId.isEmpty() && !password.isEmpty()) {

            // check if the entered username exists in database
            Employee employee = employeeDao.getEmployeeByUsername(userId);

            // Check if the password matches with the one stored in database            
            if (employee != null && employee.getEmployeePassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Logged In", "Success", JOptionPane.PLAIN_MESSAGE);
                this.dispose();
                new EmployeeDashboard(employee.getEmployeeId());
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Employee Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method that handels when a new company registers
    private void handleRegisterCompany() {
        String companyName = registerCompanyName.getText();
        String adminUserId = registerAdminUserId.getText();
        String adminPassword = new String(registerAdminPassword.getPassword());

        if (!companyName.isEmpty() && !adminUserId.isEmpty() && !adminPassword.isEmpty()) {
            Company newCompany = new Company();
            newCompany.setCompanyName(companyName);
            newCompany.setAdminUserId(adminUserId);
            newCompany.setAdminPassword(adminPassword);

            // check if the company with same name exists
            if(companyDao.checkExistence(companyName)){
                JOptionPane.showMessageDialog(this, "Company With Name "+companyName+" Already Registered", "Try Again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Adds the company to database
            if (companyDao.addCompany(newCompany)) {
                JOptionPane.showMessageDialog(this, "Company Registered Successfully", "Success", JOptionPane.PLAIN_MESSAGE);
                tabbedPane.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed. Admin User ID might already be taken.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}