package backend;

import java.util.Random;

public class Stock extends Investment {
	private double price;
	private String name;
	private String ticker;
	Stock(String ID, double p, String n, String t) {
		super(ID, "Stock");	
		setPrice(p);
		setName(n);
		setTicker(t);
	}
	
	Stock(String ID, double p, String n) {
		super(ID, "Stock");	
		setPrice(p);
		setName(n);
	}
	
	// change the price of the stock 
	public void change() { 
		Random ran = new Random();
		double x = ran.nextInt(10) - 5;
		price = price + x;
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
