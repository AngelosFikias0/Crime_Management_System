//Class used to input communication in registry
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CommsInput extends JFrame {

    private JTextField phoneNumber1Field = new JTextField("Add phone number 1: ");
    private JTextField phoneNumber2Field = new JTextField("Add phone number 2: ");
    private JTextField dateField = new JTextField("Add date (DD/MM/YYYY): ");
    private JTextField messageField = new JTextField("Add message (for SMS only): ");
    private JTextField durationField = new JTextField("Add call duration (in seconds, for phone calls only): ");
    private JButton addCommButton = new JButton("Add Communication to Registry");
    private JButton backButton = new JButton("Go back to the suspect input page");
    private JButton mainButton = new JButton("Go back to the main page");

    // Constructor takes the Registry instance so we can update it
    public CommsInput(Registry registry) {
        // Setup JFrame
        this.setTitle("Add New Communication");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center window

        // Add fields to the frame
        this.setLayout(null);
        phoneNumber1Field.setBounds(20, 20, 350, 30);
        phoneNumber2Field.setBounds(20, 60, 350, 30);
        dateField.setBounds(20, 100, 350, 30);
        messageField.setBounds(20, 140, 350, 30);
        durationField.setBounds(20, 180, 350, 30);
        addCommButton.setBounds(20, 220, 350, 30);
        backButton.setBounds(20, 260, 350, 30);
        mainButton.setBounds(20, 300, 350, 30);

        this.add(phoneNumber1Field);
        this.add(phoneNumber2Field);
        this.add(dateField);
        this.add(messageField);
        this.add(durationField);
        this.add(addCommButton);
        this.add(backButton);
        this.add(mainButton);

        // Add action listener to addCommButton to handle the communication addition
        addCommButton.addActionListener((ActionEvent e) -> {
        	String phoneNumber1 = phoneNumber1Field.getText().trim();
            String phoneNumber2 = phoneNumber2Field.getText().trim();
            String date = dateField.getText().trim();
            String message = messageField.getText().trim();
            String durationStr = durationField.getText().trim();
        	try {
        		// Check if any field is empty
                if ((phoneNumber1.isEmpty() || phoneNumber1.equals("Add phone number 1: ")) ||
                    (phoneNumber2.isEmpty() || phoneNumber2.equals("Add phone number 2: ")) ||
                    (date.isEmpty() || date.equals("Add date (DD/MM/YYYY): ")) ||
                    (durationStr.isEmpty() || durationStr.equals("Add call duration (in seconds, for phone calls only): ")) ||
                    (messageField.isVisible() && message.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                	// Parse date
                	String[] dateParts = date.split("/");
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);
                    
                    // Determine if this is a phone call or SMS and create the appropriate communication object
                    Communication newComm = null;
                    if (!message.isEmpty()&& durationStr.isEmpty()) {
                        // It's an SMS
                        newComm = new SMS(phoneNumber1, phoneNumber2, day, month, year, message);
                    } else if(message.isEmpty()&& !durationStr.isEmpty()){
                        // It's a phone call
                        int duration = Integer.parseInt(durationStr);
                        newComm = new PhoneCall(phoneNumber1, phoneNumber2, day, month, year, duration);
                    }

                    // Add the new communication to the registry
                    registry.addCommunication(newComm);

                    // Show success message and return to the FindSuspect screen
                    JOptionPane.showMessageDialog(null, "Communication added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();  // Close the current frame
                    new Input(registry); // Go back to the FindSuspect screen
                }catch(Exception ex) {
                	JOptionPane.showMessageDialog(null, "Date must be in correct format", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
        	}catch(Exception ex) {
        		 JOptionPane.showMessageDialog(null, "Input data in the fields correctly", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        });

        mainButton.addActionListener(e->{
        	this.dispose();
        	new FindSuspect(registry);
        });
        
        backButton.addActionListener(e->{
        	this.dispose();
        	new Input(registry);
        });
        
        // Call the setup for clearing text fields when clicked
        setupClearFields();

        this.setVisible(true);
    }

    // Method to clear the placeholder text when clicked
    private void setupClearFields() {
        // For each text field, add a MouseListener to clear placeholder text on click
        addClearOnClick(phoneNumber1Field, "Add phone number 1: ");
        addClearOnClick(phoneNumber2Field, "Add phone number 2: ");
        addClearOnClick(dateField, "Add date (DD/MM/YYYY): ");
        addClearOnClick(messageField, "Add message (for SMS only): ");
        addClearOnClick(durationField, "Add call duration (in seconds, for phone calls only): ");
    }

    private void addClearOnClick(JTextField field, String placeholder) {
        field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }
            }
        });
    }
}
