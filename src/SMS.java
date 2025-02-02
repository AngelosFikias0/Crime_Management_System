//SMS subclass
public class SMS extends Communication {
	//Field initialization, it initializes the field specific to this class
    private String message;

    //Constructor with inherited initialization of fields from the superclass other than the message
    public SMS(String num1, String num2, int year, int month, int day, String message) {
        super(num1, num2, year, month, day);
        this.message = message;
    }

    //Print info method overridden from the inherited superclass Communication
    @Override
    public void printInfo() {
        System.out.println("This SMS has the following info");
        System.out.println("Between " + num1 + " --- " + num2);
        System.out.println("on " + year + "/" + month + "/" + day);
        System.out.println("Text: " + message);
    }

    //Getter of the message of the SMS
    public String getMessage() { 
    	return message; 
	}
}