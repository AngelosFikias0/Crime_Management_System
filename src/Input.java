//Class used to input a suspect to the registry
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Input extends JFrame {

    private JTextField nameField = new JTextField("Add suspect's name: ");
    private JTextField codeField = new JTextField("Add suspect's code name: ");
    private JTextField cityField = new JTextField("Add suspect's city: ");
    private JTextField phoneField = new JTextField("Add suspect's phone number(s) separated by commas: ");
    private JButton createButton = new JButton("Add Suspect to Registry");
    private JButton comButton = new JButton("Add Communication to Registry");
    private JButton mainButton = new JButton("Go back to the main page");

    // Constructor takes the Registry instance so we can update it
    public Input(Registry registry) {
        // Setup JFrame
        this.setTitle("Add New Suspect");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center window

        // Add fields to the frame
        this.setLayout(null);
        nameField.setBounds(20, 20, 350, 30);
        codeField.setBounds(20, 60, 350, 30);
        cityField.setBounds(20, 100, 350, 30);
        phoneField.setBounds(20, 140, 350, 30);
        createButton.setBounds(20, 200, 350, 30);
        comButton.setBounds(20, 240, 350, 30); 
        mainButton.setBounds(20, 280, 350, 30); 

        this.add(nameField);
        this.add(codeField);
        this.add(cityField);
        this.add(phoneField);
        this.add(createButton);
        this.add(comButton);
        this.add(mainButton);

        // Add action listener to createButton to handle the suspect addition (with lambda)
        createButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String codeName = codeField.getText().trim();
            String city = cityField.getText().trim();
            String phoneNumbers = phoneField.getText().trim();

            // Check if any field is empty
            if ((name.isEmpty() || name.equals("Add suspect's name: ")) || 
                    (codeName.isEmpty() || codeName.equals("Add suspect's code name: ")) || 
                    (city.isEmpty() || city.equals("Add suspect's city: ")) || 
                    (phoneNumbers.isEmpty() || phoneNumbers.equals("Add suspect's phone number(s) separated by commas: "))) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create new suspect object
            Suspect newSuspect = new Suspect(name, codeName, city);

            // Add phone numbers to the suspect
            String[] numbers = phoneNumbers.split(",");
            for (String number : numbers) {
                newSuspect.addNumber(number.trim());
            }

            // Add the new suspect to the registry
            registry.addSuspect(newSuspect);

            // Show success message and return to FindSuspect screen
            JOptionPane.showMessageDialog(null, "Suspect added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();  // Close the current frame
            new FindSuspect(registry); // Go back to the FindSuspect screen
        });

        // Add action listener to comButton (lambda)
        comButton.addActionListener(e ->{
        	new CommsInput(registry);
        	this.dispose();
        });

        mainButton.addActionListener(e->{
        	this.dispose();
        	new FindSuspect(registry);
        });
        
        // Call the setup for clearing text fields when clicked
        setupClearFields();

        this.setVisible(true);
    }

    // Method to clear the placeholder text when clicked
    private void setupClearFields() {
        // For each text field, add a MouseListener to clear placeholder text on click
        addClearOnClick(nameField, "Add suspect's name: ");
        addClearOnClick(codeField, "Add suspect's code name: ");
        addClearOnClick(cityField, "Add suspect's city: ");
        addClearOnClick(phoneField, "Add suspect's phone number(s) separated by commas: ");
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
