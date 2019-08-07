package backend;

public class Stock extends Investment {
	private double price;
	private String name;
	private String ticker;
	Stock(String ID, double p, String n, String t) {
		super(ID);	
		setPrice(p);
		setName(n);
		setTicker(t);
	}
	
	// change the price of the stock 
	private void change() { 
		// to be continued
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(id + "-" + name + "-" + ticker + "-" + price);
		return sb.toString();
	}
	
	private void setTicker(String t) {
		this.ticker = t;
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
