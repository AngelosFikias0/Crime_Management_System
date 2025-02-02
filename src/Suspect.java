//Suspect class
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Suspect {
	
	//Field initialization
    private String name;
    private String codeName;
    @SuppressWarnings("unused")
	private String city;
    private ArrayList<String> numbers = new ArrayList<>();
    private ArrayList<Suspect> partners = new ArrayList<>();
    
    //Constructor
    public Suspect(String name, String code_name, String city) {
    	this.name = name;
    	this.codeName = code_name;
    	this.city = city;
    }
    
    //Adds a number to the arraylist numbers of the suspect
    public void addNumber(String number) {
    	numbers.add(number);
    }
    
    //Adds a partner to the arraylist partners of the suspect
    public void addPartner(Suspect partner) { 
    	if(!partners.contains(partner)) {
    		partners.add(partner);
    	}
    }
    
    //Returns True or False depending if the suspect given is connected to the suspect called upon
    public boolean isConnectedTo(Suspect anotherSuspect) { 
    	if (partners.contains(anotherSuspect)) {
    	    return true;
    	}
    	return false;
    }
    
    //Returns common partners of the the suspect given to the suspect called upon the method
    public ArrayList<Suspect> getCommonPartners(Suspect anotherSuspect) { 
        ArrayList<Suspect> commonPartners = new ArrayList<>();
        
        for (Suspect partner : anotherSuspect.getPartners()) {
            if (this.isConnectedTo(partner)) { 
                commonPartners.add(partner);
            }
        }
        return commonPartners;
    }

    
    // Method for suggested partners (Triangular closure rule)
    public ArrayList<Suspect> getSuggestedPartners() {
        Set<Suspect> suggestedPartners = new HashSet<>();
        
        for (Suspect partner : this.getPartners()) {
            for (Suspect secondPartner : partner.getPartners()) {
            	//Check if it is already a partner, or if it is reffering to itself
                if (!this.isConnectedTo(secondPartner) && !this.name.equals(secondPartner.getName())) {
                    suggestedPartners.add(secondPartner);
                }
            }
        }
        return new ArrayList<>(suggestedPartners);
    }

    
    //Getter for name
	public String getName() {
		return name;
	}

	//Getter for code name
	public String getCodeName() {
		return codeName;
	}

	//Getter for numbers arraylist
	public ArrayList<String> getNumbers() {
		return numbers;
	}

	//Getter for partners arraylist
	public ArrayList<Suspect> getPartners() {
		return partners;
	}

}
