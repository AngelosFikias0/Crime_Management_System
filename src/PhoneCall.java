//PhoneCall subclass
public class PhoneCall extends Communication {
	//Field initialization, it initializes the field specific to this class
    private int duration;

    //Constructor with inherited initialization of fields from the superclass other than the duration
    public PhoneCall(String num1, String num2, int day, int month, int year, int duration) {
        super(num1, num2, day, month, year);
        this.duration = duration;
    }

    //Print info method overridden from the inherited superclass Communication
    @Override
    public void printInfo() {
        System.out.println("This phone call has the following info");
        System.out.println("Between " + num1 + " --- " + num2);
        System.out.println("on " + year + "/" + month + "/" + day);
        System.out.println("Duration: " + duration);
    }

    //Getter of the duration of calls
    public int getDuration() { 
    	return duration; 
	}
}