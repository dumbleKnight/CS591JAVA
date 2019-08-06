public class Bond extends Investment{
	protected double interest;
	protected double price;
	protected String name;
	
	
	Bond(String ID, double interest, double price, String name) {
		super(ID, "Bond");
		this.interest = interest;
		this.price = price;
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
	/*static public enum InterestRate{
		WEEK, MONTH, YEAR
	}*/
}
