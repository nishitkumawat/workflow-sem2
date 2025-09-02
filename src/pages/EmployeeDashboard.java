package pages;

import dao.BroadcastDao;
import dao.EmployeeDao;
import dao.MessageDao;
import models.Broadcast;
import models.Employee;
import utils.BroadcastQueue;
import utils.ButtonMap;
import utils.EmployeeList;
import utils.MessageQueue;
import utils.UIelements;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class EmployeeDashboard extends JFrame {

    private int employeeId;
    private int companyId;
    private String employeeName;

    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel chatPanel;
    private JPanel messagePanel;
    private JScrollPane chatScrollPane;
    private JTextField messageField;
    private JButton sendButton;
    private JPanel broadcastPanel;
    private JPanel broadcastContentPanel;
    private JScrollPane broadcastScrollPane;
    private JPanel employeeButtonsPanel;
    private JPanel topPanel;

    private EmployeeDao employeeDao;
    private MessageDao messageDao;
    private BroadcastDao broadcastDao;

    private Timer refreshTimer;
    private Employee selectedEmployee;
    // private Map<Integer, JButton> employeeButtons;
    private ButtonMap employeeButtons;

    private static final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    private static final Color SENT_MESSAGE_COLOR = new Color(190, 215, 220);
    private static final Color RECEIVED_MESSAGE_COLOR = new Color(251,249,241);
    private static final Color SELECTED_BUTTON_COLOR = new Color(41, 128, 185);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font TEXT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public EmployeeDashboard(int employeeId) {
        this.employeeId = employeeId;
        this.employeeDao = new EmployeeDao();
        this.messageDao = new MessageDao();
        this.broadcastDao = new BroadcastDao();
        // this.employeeButtons = new HashMap<>();
        this.employeeButtons = new ButtonMap();

        Employee employee = employeeDao.getEmployeeById(employeeId);
        this.companyId = employee.getCompanyId();
        this.employeeName = employee.getEmployeeName();

        setTitle("Professional Chat - " + employeeName);
        setMinimumSize(new Dimension(900, 700));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        layoutComponents();
        initializeRefreshTimer();

        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(SECONDARY_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
        // Top Panel
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(getWidth(), 45));
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setLayout(new BorderLayout(10, 0));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, ACCENT_COLOR));
    
        // Company logo Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel logoLabel = new JLabel(new ImageIcon("src\\images\\logo.png"));
        logoPanel.add(logoLabel);
        topPanel.add(logoPanel, BorderLayout.WEST);
    
        // Center of top panel (User info Panel)
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        userInfoPanel.setOpaque(false);
    
        JPanel nameRolePanel = new JPanel();
        nameRolePanel.setLayout(new BoxLayout(nameRolePanel, BoxLayout.Y_AXIS));
        nameRolePanel.setOpaque(false);
    
        JLabel nameLabel = new JLabel(employeeName);
        nameLabel.setFont(HEADER_FONT);
        nameLabel.setForeground(Color.WHITE);
        nameRolePanel.add(nameLabel);
    
        userInfoPanel.add(nameRolePanel);
        topPanel.add(userInfoPanel, BorderLayout.CENTER);
    
        // Right side of top panel (Logout Panel)
        JPanel optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);
    
        JButton logoutButton = createTopPanelButton("Logout");
        logoutButton.addActionListener(e -> logoutUser());
        optionsPanel.add(logoutButton);
    
        topPanel.add(optionsPanel, BorderLayout.EAST);
    
        // Sidebar (Employee Buttons)
        sidebarPanel = new JPanel(new BorderLayout(0, 10));
        sidebarPanel.setBackground(PRIMARY_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
    
        JLabel employeesLabel = new JLabel("Employees", SwingConstants.CENTER);
        employeesLabel.setFont(HEADER_FONT);
        employeesLabel.setForeground(SECONDARY_COLOR);
        employeesLabel.setBorder(new EmptyBorder(15, 0, 15, 0));
        sidebarPanel.add(employeesLabel, BorderLayout.NORTH);
    
        employeeButtonsPanel = new JPanel();
        employeeButtonsPanel.setLayout(new BoxLayout(employeeButtonsPanel, BoxLayout.Y_AXIS));
        employeeButtonsPanel.setBackground(PRIMARY_COLOR);
    
        JScrollPane employeeScrollPane = new JScrollPane(employeeButtonsPanel);
        employeeScrollPane.setBorder(BorderFactory.createEmptyBorder());
        sidebarPanel.add(employeeScrollPane, BorderLayout.CENTER);
    
        // Chat Panel
        chatPanel = new JPanel(new BorderLayout(0, 10));
        chatPanel.setBackground(SECONDARY_COLOR);
    
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(SECONDARY_COLOR);
    
        chatScrollPane = new JScrollPane(messagePanel);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
    
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(SECONDARY_COLOR);
    
        messageField = new JTextField();
        messageField.setFont(TEXT_FONT);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    
        // sendButton = new JButton("Send");
        // sendButton.setFont(TEXT_FONT);
        // sendButton.setBackground(ACCENT_COLOR);
        // sendButton.setForeground(SECONDARY_COLOR);
        // sendButton.setFocusPainted(false);
        // sendButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        sendButton = UIelements.createStyledButton("Send");
        sendButton.addActionListener(e -> sendMessage());
    
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
    
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
    
        // Broadcast Panel
        broadcastPanel = new JPanel(new BorderLayout(0, 10));
        broadcastPanel.setBackground(SECONDARY_COLOR);
        broadcastPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR),
            "Company Broadcasts",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            HEADER_FONT,
            PRIMARY_COLOR
        ));
    
        broadcastContentPanel = new JPanel();
        broadcastContentPanel.setLayout(new BoxLayout(broadcastContentPanel, BoxLayout.Y_AXIS));
        broadcastContentPanel.setBackground(SECONDARY_COLOR);
    
        broadcastScrollPane = new JScrollPane(broadcastContentPanel);
        broadcastScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        broadcastScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        broadcastScrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        broadcastPanel.add(broadcastScrollPane, BorderLayout.CENTER);
    
        loadEmployees();
        loadBroadcasts();
    }

    private void layoutComponents() {
        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(chatPanel, BorderLayout.CENTER);
        mainPanel.add(broadcastPanel, BorderLayout.EAST);
        setContentPane(mainPanel);
    }

    // Get the employees who work for the same company as the one logged in
    private void loadEmployees() {
        EmployeeList employees = employeeDao.getEmployeesByCompanyId(companyId);
        employeeButtonsPanel.removeAll();
        employeeButtons.clear();
        for (Employee employee : employees) {
            if (employee.getEmployeeId() != employeeId) {
                JButton employeeButton = new JButton(employee.getEmployeeName());
                employeeButton.setFont(TEXT_FONT);
                employeeButton.setBackground(PRIMARY_COLOR);
                employeeButton.setForeground(SECONDARY_COLOR);
                employeeButton.setBorderPainted(false);
                employeeButton.setFocusPainted(false);
                employeeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                employeeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                employeeButton.addActionListener(e -> selectEmployee(employee));
                employeeButtonsPanel.add(employeeButton);
                employeeButtonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                employeeButtons.put(employee.getEmployeeId(), employeeButton);
            }
        }
        employeeButtonsPanel.revalidate();
        employeeButtonsPanel.repaint();
    }

    /**
     * Handles the selection of an employee from the list
     * @param employee The selected Employee object
     */
    private void selectEmployee(Employee employee) {
        if (selectedEmployee != null) {
            JButton prevButton = employeeButtons.get(selectedEmployee.getEmployeeId());
            if (prevButton != null) {
                prevButton.setBackground(PRIMARY_COLOR);
            }
        }

        selectedEmployee = employee;
        JButton selectedButton = employeeButtons.get(selectedEmployee.getEmployeeId());
        if (selectedButton != null) {
            selectedButton.setBackground(SELECTED_BUTTON_COLOR);
        }

        loadChat(selectedEmployee);
    }

    /**
     * Loads and displays the chat history with the selected employee
     * @param selectedEmployee The Employee object of the selected chat partner
     */
    private void loadChat(Employee selectedEmployee) {
        if (selectedEmployee != null) {
            MessageQueue messages = messageDao.getMessages(employeeId, selectedEmployee.getEmployeeId());
            messagePanel.removeAll();

            for (String message : messages) {
                String[] parts = message.split(": ", 2);
                if (parts.length == 2) {
                    JPanel messageItemPanel = createMessageItemPanel(parts[0], parts[1]);
                    messagePanel.add(messageItemPanel);
                    messagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }

            messagePanel.revalidate();
            messagePanel.repaint();

            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }
    }

    /**
     * Creates a panel to display a single message in the chat
     * @param sender The name of the message sender
     * @param content The content of the message
     * @return JPanel representing the message
     */
    private JPanel createMessageItemPanel(String sender, String content) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(sender.equals(employeeName) ? SENT_MESSAGE_COLOR : RECEIVED_MESSAGE_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setAlignmentX(sender.equals(employeeName) ? Component.LEFT_ALIGNMENT : Component.RIGHT_ALIGNMENT);
        panel.setBorder(BorderFactory.createTitledBorder(sender.equals(employeeName)?"You":sender));

        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(TEXT_FONT);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setOpaque(false);
        contentArea.setEditable(false);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(contentArea);

        return panel;
    }

    // Method to handle the insertion of messages in database
    private void sendMessage() {
        if (selectedEmployee != null && !messageField.getText().isEmpty()) {
            boolean sent = messageDao.sendMessage(employeeId, selectedEmployee.getEmployeeId(), messageField.getText());
            if (sent) {
                messageField.setText("");
                loadChat(selectedEmployee);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send message", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // This Method gets the broadcasted messages and add them to panel which contains all the broadcasts
    private void loadBroadcasts() {
        BroadcastQueue broadcasts = broadcastDao.getBroadcastsByCompanyId(companyId);
        broadcastContentPanel.removeAll();

        int size = broadcasts.size();
        for (int i=0 ; i<size ; i++) {
            Broadcast broadcast = broadcasts.dequeue();
            JPanel broadcastItemPanel = createBroadcastItemPanel(broadcast);
            broadcastContentPanel.add(broadcastItemPanel);
            broadcastContentPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        }

        broadcastContentPanel.revalidate();
        broadcastContentPanel.repaint();
    }

    private void logoutUser(){
        int response = JOptionPane.showConfirmDialog(this,"Are You Sure?");
        if(response==JOptionPane.YES_OPTION){
            this.dispose();
            new homePage();
        }
    }

    private JButton createTopPanelButton(String text) {
        JButton button = new JButton(text);
        button.setFont(TEXT_FONT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.gray);
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });
    
        return button;
    }

    /**
     * Creates a panel to display a single broadcast message
     * @param broadcast The Broadcast object to display
     * @return JPanel representing the broadcast message
     */
    private JPanel createBroadcastItemPanel(Broadcast broadcast) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel timestampLabel = new JLabel(broadcast.getTimestamp().toString());
        timestampLabel.setFont(TEXT_FONT.deriveFont(Font.BOLD));
        timestampLabel.setForeground(PRIMARY_COLOR);

        JTextArea messageArea = new JTextArea(broadcast.getMessage());
        messageArea.setFont(TEXT_FONT);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);
        messageArea.setOpaque(false);
        messageArea.setEditable(false);
        messageArea.setForeground(TEXT_COLOR);

        panel.add(timestampLabel, BorderLayout.NORTH);
        panel.add(messageArea, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Initializes a timer to periodically refresh the chat and broadcasts
     */
    private void initializeRefreshTimer() {
        refreshTimer = new Timer();
        refreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (selectedEmployee != null) {
                        loadChat(selectedEmployee);
                    }
                    loadBroadcasts();
                });
            }
        }, 0, 3000);
    }

}