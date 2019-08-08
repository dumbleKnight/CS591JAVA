public class Bond extends Investment{
	protected double interest;
	protected String name;
	
	
	Bond(String ID, double interest, String name) {
		super(ID, "Bond");
		this.interest = interest;
		this.name = name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bond ID: " + id);
		return sb.toString();
	}
	
	public double getInterest() {
		return this.interest;
	}
	
	public String getName() {
		return name;
	}
	/*static public enum InterestRate{
		WEEK, MONTH, YEAR
	}*/
}
