
public class Stock extends Investment {
	private double price;
	private String name;
<<<<<<< HEAD
	private double amount; 
	Stock(String ID, double p, String n, double amount) {
=======
	private int amount; //there's only a fixed amount of stock that users can buy
	Stock(String ID, double p, String n) {
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		super(ID, "Stock");	
		setPrice(p);
		setName(n);
		this.amount = amount;
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
	
	public double getAmount() {
		return amount;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
