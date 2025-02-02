//Communication superclass
public abstract class Communication {
	//Field initialization
    protected String num1;
    protected String num2;
    protected int year;
    protected int month;
    protected int day;
    
    //Constructor
    public Communication(String num1, String num2, int day, int month, int year) {
        this.num1 = num1;
        this.num2 = num2;
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    //Print info method that is inherited by the subclasses
    public abstract void printInfo();
    	//Abstract method to print communication details.
    
    //Initialization of getDuration of the PhoneCall subclass here for implementation in registry class
    public int getDuration() { 
    	return -1; 
	}
    
    //Initialization of the getMessage of the SMS subclass here for implementation in registry class
    public String getMessage() { 
    	return ""; 
	}
    
    //Getter for num1
	public String getNum1() {
		return num1;
	}

	//Getter for num2
	public String getNum2() {
		return num2;
	}

}
