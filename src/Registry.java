//Registry class
import java.util.ArrayList;

public class Registry {
	//Field initialization
    private ArrayList<Suspect> suspects;
    private ArrayList<Communication> communications;

    //Constructor
    public Registry() {
        suspects = new ArrayList<>();
        communications = new ArrayList<>();
    }

    //Adds suspect to the suspects arraylist
    public void addSuspect(Suspect suspect) {
        if (!suspects.contains(suspect)) {
            suspects.add(suspect);
        }
    }
    
    //Adds communication to the communications arraylist
    public void addCommunication(Communication comm) {
        communications.add(comm);
        
        //Uses the helper method getSuspectByNumber to find the suspect with that number
        Suspect suspect1 = getSuspectByNumber(comm.getNum1());
        Suspect suspect2 = getSuspectByNumber(comm.getNum2());
        
        //If suspect 1 and 2 don't exist yet as partners then make them
        if (suspect1 != null && suspect2 != null) {
            suspect1.addPartner(suspect2);
            suspect2.addPartner(suspect1);
        }
    }

    //Helper method for addCommunication
    private Suspect getSuspectByNumber(String number) {
        for (Suspect suspect : suspects) {
        	//For each suspect in the registry, if its number arraylist contains that specific number return them
            if (suspect.getNumbers().contains(number)) {
                return suspect;
            }
        }
        return null; 
    }

    //Returns the suspect with the most partners
    public Suspect getSuspectWithMostPartners() {
    	//Initializes variables used in the method
        Suspect suspectWithMostPartners = null;
        int maxPartners = -1; 

        //For each suspect in the suspects arraylist of registry compare size of partners to return the one suspect with the most
        for (Suspect s : suspects) {
            int partnersCount = s.getPartners().size();
            
            if (partnersCount > maxPartners) {
                maxPartners = partnersCount;
                suspectWithMostPartners = s;
            }
        }
        
        return suspectWithMostPartners;
    }

    //Returns the longest phone call between two suspects
    public PhoneCall getLongestPhoneCallBetween(String num1, String num2) {
    	//Initializes object and variable used in the method
    	PhoneCall longestPhoneCall = null;
        int longestDuration = 0;
        
        //For each communication in the communications arraylist of the registry compare the durations and find the max
        for (Communication comm : communications) {
        	//Checks if comm is a phone call and not SMS 
            if (comm.getDuration()!=-1) {
            	
            	//Finds a phone call between the two numbers
                if ((comm.getNum1().equals(num1) && comm.getNum2().equals(num2)) ||
                    (comm.getNum1().equals(num2) && comm.getNum2().equals(num1))) {
                	
                    //Finds the longest one
                    if (comm.getDuration() > longestDuration) {
                        longestDuration = comm.getDuration();
                        
                        //Casting
                        longestPhoneCall = (PhoneCall) comm;
                    }
                }
            }
        }
        return longestPhoneCall;
    }

    //Returns the messages between two numbers
    public ArrayList<SMS> getMessagesBetween(String num1, String num2) {
    	//Initializes the arraylist used in the method and the SMS instance
        ArrayList<SMS> messagesBetween = new ArrayList<>();
        SMS sms;
        
        for (Communication comm : communications) {
        	//Checks if comm is a SMS and not a phone call 
            if (comm.getMessage()!="") {
            	//Casting
                sms = (SMS) comm; 
                
                //Finds a SMS between the two numbers
                if ((sms.getNum1().equals(num1) && sms.getNum2().equals(num2)) ||
                    (sms.getNum2().equals(num2) && sms.getNum2().equals(num1))) {
                	
                	String[] words = {"Bomb", "Attack", "Explosives", "Gun"};
                	for(String w : words) {
                		//If the SMS contains sensitive words it adds it to the arraylist
                		if(sms.getMessage().contains(w)) {
                			messagesBetween.add(sms);
                		}
                	}
                }
            }
        }
        return messagesBetween;
    }
    
    //Checks if suspect exists
    public boolean suspectExists(Suspect suspect) {
    	return suspects.contains(suspect);
    }

    //Returns all suspects in registry
	public ArrayList<Suspect> getSuspects() {
		return suspects;
	}

}
