//GUI class that shows the suspect's info
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SuspectPage extends JFrame {
    
    @SuppressWarnings("unused")
	public SuspectPage(Suspect suspect, Registry registry) {
        
        // ----------- 1. TOP PANEL - Name, Code Name, and Phone Numbers -----------
        JPanel topPanel = new JPanel(new BorderLayout());

        // Name Field
        JTextField nameField = new JTextField(15);
        nameField.setText("Name: " + suspect.getName());
        nameField.setEditable(false);

        // Code Name Field
        JTextField codeNameField = new JTextField(15);
        codeNameField.setText("Code Name: " + suspect.getCodeName());
        codeNameField.setEditable(false);

        // Phone Numbers Area
        JTextArea phoneNumbersArea = new JTextArea(5, 20);
        phoneNumbersArea.setEditable(false); // Make it non-editable
        StringBuilder phoneNumbersBuilder = new StringBuilder();

        for (String number : suspect.getNumbers()) {
            phoneNumbersBuilder.append(number).append("\n"); // Append each number with newline
        }
        phoneNumbersArea.setText("Phone Numbers:\n" + phoneNumbersBuilder.toString());

        // Add components to Top Panel
        topPanel.add(nameField, BorderLayout.WEST);
        topPanel.add(codeNameField, BorderLayout.CENTER);
        topPanel.add(phoneNumbersArea, BorderLayout.EAST);
        
        // ----------- 2. SMS PANEL - Display Suspicious SMS -----------
        JPanel smsPanel = new JPanel(new BorderLayout());

        // Input Field for Phone Number
        JTextField phoneInputField = new JTextField(15);

        // Text Area for SMS
        JTextArea smsArea = new JTextArea(5, 30);
        smsArea.setEditable(false);

        // Button to Fetch SMS
        JButton findSMSButton = new JButton("Find SMS");
        findSMSButton.addActionListener(e -> {
            String inputNumber = phoneInputField.getText();
            StringBuilder smsBuilder = new StringBuilder();

            // Fetch messages between input number and suspect's numbers
            for (String number : suspect.getNumbers()) {
                for (SMS message : registry.getMessagesBetween(number, inputNumber)) {
                    smsBuilder.append(message.getMessage()).append("\n");
                }
            }
            if(!smsBuilder.toString().isEmpty()) {
            	smsArea.setText("Suspicious SMS:\n" + smsBuilder.toString());
            }else {
            	if(smsArea.getText().isEmpty()) {
            		smsArea.setText("-");
            		JOptionPane.showMessageDialog(smsArea, "Please enter a number in the required field.", "Input Error", JOptionPane.ERROR_MESSAGE);
            	}else {
            		smsArea.setText("Didn't find any correlation");
            	}
            }
        });

        // Add components to SMS Panel
        smsPanel.add(phoneInputField, BorderLayout.WEST);
        smsPanel.add(new JScrollPane(smsArea), BorderLayout.CENTER);
        smsPanel.add(findSMSButton, BorderLayout.EAST);

        // ----------- 3. PARTNERS PANEL - Display Suspect's Partners -----------
        JPanel partnersPanel = new JPanel(new BorderLayout());
        JLabel partnersLabel = new JLabel("Partners: ");
        JTextArea partnersArea = new JTextArea(5, 30);
        partnersArea.setEditable(false);

        // Fetch and sort partners
        ArrayList<Suspect> partners = suspect.getPartners();
        bubbleSortByName(partners);

        // Display partners
        StringBuilder partnersBuilder = new StringBuilder();
        for (Suspect partner : partners) {
            partnersBuilder.append(partner.getName()).append(", ").append(partner.getCodeName()).append("\n");
        }
        partnersArea.setText(partnersBuilder.toString());

        // Add components to Partners Panel
        partnersPanel.add(partnersLabel, BorderLayout.NORTH);
        partnersPanel.add(new JScrollPane(partnersArea), BorderLayout.CENTER);

        // ----------- 4. SUGGESTED PARTNERS PANEL -----------
        JPanel suggestedPanel = new JPanel(new BorderLayout());
        JLabel suggestedLabel = new JLabel("Suggested Partners ----->");
        JTextArea suggestedArea = new JTextArea(3, 30);
        suggestedArea.setEditable(false);

        // Fetch and sort suggested partners
        ArrayList<Suspect> suggestedPartners = suspect.getSuggestedPartners();
        bubbleSortByName(suggestedPartners);

        // Display suggested partners
        StringBuilder suggestedBuilder = new StringBuilder();
        for (Suspect suggested : suggestedPartners) {
            suggestedBuilder.append(suggested.getName()).append(", ").append(suggested.getCodeName()).append("\n");
        }
        suggestedArea.setText(suggestedBuilder.toString());

        // Add components to Suggested Panel
        suggestedPanel.add(suggestedLabel, BorderLayout.NORTH);
        suggestedPanel.add(new JScrollPane(suggestedArea), BorderLayout.CENTER);

        // ----------- 5. BOTTOM PANEL - Back Button -----------
        JPanel bottomPanel = new JPanel();
        JButton backButton = new JButton("Back to Search Screen");

        backButton.addActionListener(e -> {
            new FindSuspect(registry); // Open FindSuspect screen
            this.dispose(); // Close current window
        });
        bottomPanel.add(backButton);

        // ----------- FRAME LAYOUT -----------
        this.setTitle("Suspect Page (" + suspect.getName() + ")");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,500);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Add panels to the frame with spacing
        this.add(topPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10))); // Vertical spacing
        this.add(smsPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(partnersPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(suggestedPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(bottomPanel);

        this.setVisible(true);
    }
    
    // ----------- UTILITY: Bubble Sort by Suspect Name -----------
    private void bubbleSortByName(ArrayList<Suspect> suspects) {
        int n = suspects.size();

        // Standard Bubble Sort Algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (suspects.get(j).getName().compareTo(suspects.get(j + 1).getName()) > 0) {
                    // Swap elements if they are out of order
                    Suspect temp = suspects.get(j);
                    suspects.set(j, suspects.get(j + 1));
                    suspects.set(j + 1, temp);
                }
            }
        }
    }
}