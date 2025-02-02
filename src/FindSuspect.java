//GUI class to search any suspect or visualize the network
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FindSuspect extends JFrame {

    private JPanel panel = new JPanel();
    
    private JButton findButton = new JButton("Find");
    private JButton vizButton = new JButton("Visualize Network");
    private JButton inputButton = new JButton("Add suspect to registry");
    private JButton printButton = new JButton("Print suspects to registry");
    
    private JTextField searchField = new JTextField("Please enter the suspect's name", 30);

    public FindSuspect(Registry registry) {
    	
        setupSearchField();
        
        panel.add(searchField);
        panel.add(findButton);
        panel.add(vizButton);
        panel.add(inputButton);
        panel.add(printButton);

        this.setContentPane(panel);
        this.setSize(500,400);
        this.setTitle("Find Suspect");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        setupButtons(registry);
    }

    // Clears placeholder text when the search field is clicked
    private void setupSearchField() {
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (searchField.getText().equals("Please enter the suspect's name")) {
                    searchField.setText("");
                }
            }
        });
    }

    // Adds functionality to the "Find" button
    @SuppressWarnings("unused")
	private void setupButtons(Registry registry) {
    	//Event listener for find button
        findButton.addActionListener(e -> {
            String suspectName = searchField.getText().trim();
            Suspect foundSuspect = null;

            // Search for the suspect
            for (Suspect suspect : registry.getSuspects()) {
                if (suspect.getName().equalsIgnoreCase(suspectName)) {
                    foundSuspect = suspect;
                    break;
                }
            }

            // Show results based on whether the suspect was found
            if (registry.suspectExists(foundSuspect)) {
                new SuspectPage(foundSuspect, registry);
                this.dispose();
            } else {
                if (suspectName.isEmpty() || suspectName.equals("Please enter the suspect's name")) {
                    JOptionPane.showMessageDialog(searchField, "Please enter a name to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(searchField, "Suspect '" + suspectName + "' was not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        //Event listener for add suspect to registry button
        inputButton.addActionListener(e->{
        	new Input(registry);
        	this.dispose();
        });
        
        //Event listener for print suspect to file button
        printButton.addActionListener(e->{
        	
        	File file = new File("Suspects.txt");
        	
        	try {
				FileWriter writer = new FileWriter(file);
				int c=1;
				
				for(Suspect s : registry.getSuspects()) {
					writer.write(c+") "+"[Suspect's Name]: "+s.getName()+", [Suspect's Code Name]: "+s.getCodeName()+", [Suspect's Numbers]: ");
					
					for(String n : s.getNumbers()) {
						writer.write(n+" ");
					}
					
					writer.write(System.lineSeparator());
					c++;
				}
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        });
        
        //Event listener for visualize network button
        vizButton.addActionListener(e->{
        	new Visualize(registry);
        });
    }
}