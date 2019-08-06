package backend;

public class Bond extends Investment{
	protected double weeklyInterestRate;
	protected double monthlyInterestRate;
	protected double yearlyInterestRate;
	
	Bond(String ID) {
		super(ID);
		weeklyInterestRate = 0.02;
		monthlyInterestRate = 0.03;
		yearlyInterestRate = 0.06;
	}
	
	Bond(String ID, double d1, double d2, double d3) {
		super(ID);
		weeklyInterestRate = d1;
		monthlyInterestRate = d2;
		yearlyInterestRate = d3;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bond ID: " + id);
		return sb.toString();
	}
	
	public double getWeekly() {
		return  this.weeklyInterestRate;
	}
	
	public double getMonthly() {
		return this.monthlyInterestRate;
	}
	
	public double getYearly() {
		return this.yearlyInterestRate;
	}
	/*static public enum InterestRate{
		WEEK, MONTH, YEAR
	}*/
}
