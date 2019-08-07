
public class Stock extends Investment {
	private double price;
	private String name;
	//private double amount; 
	Stock(String ID, double p, String n) {
		super(ID, "Stock");	
		setPrice(p);
		setName(n);
	}
	
	// change the price of the stock 
	private void change() { 
		// to be continued
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Stock ID: " + id);
		return sb.toString();
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
