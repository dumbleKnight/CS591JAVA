package backend;

import java.time.Duration;
import java.time.Instant;

public class Property {
	private Investment investment;
	private Instant now;
	private Instant due;
	private double amount; // stock : number of share, bond : 
	private double interest; // used for bond
	
	static public enum InterestRate{
		WEEK, MONTH, YEAR
	}
	
	Property(Investment i, Instant now, Instant due, double amount, double interest) {
		//for initializing bond from DB 
		this.investment = i;
		this.now = now;
		this.due = due;
		this.amount = amount;
		this.interest = interest;
	}
	
	Property(double a, Investment i, Instant now) {
		amount = a;
		investment = i;
		this.now = now;
	}
	
	Property(double a, Investment i) {
		//why do we need now
		amount = a;
		investment = i;
		now = Instant.now();
	}
	
	Property(double a, Investment i, InterestRate irate, double in){
		amount = a;
		investment = i;
		now = Instant.now();
		interest = in;
		switch(irate) {
			case WEEK:
				due = now.plus(Duration.ofSeconds(3));
				break;
			
			case MONTH:
				due = now.plus(Duration.ofSeconds(7));
				break;
			
			default:
				due = now.plus(Duration.ofSeconds(10));
		}
	}
	

	public String toString() {
		StringBuilder sp = new StringBuilder();
		sp.append(investment);
		sp.append("-");
		sp.append(amount);
		
		return sp.toString();
	}
	
	//only can be triggered if the property is stock
	public boolean add(double am) {
		if(investment instanceof Stock) {
			amount = amount + am;
		}
		return false;
	}
	
	//sell bond
	public double sell() {
		Instant temp = Instant.now();
		double res;
		if(Duration.between(temp, due).getSeconds() >= 0.0) {
			res = amount * (1 + interest);
		}else {
			res = amount * 1.0;
		}
		amount = 0.0;
		return res;
	}
	
	// sell stock at price with amount of sale
	public double sell(double am, double price) {
		double res;
		if(am > amount) {
			res = 0.0;
		}else {
			res = am * price;
			amount = amount - am;
		}
		return res;
	}
	
	public boolean empty() {
		if(amount == 0.0) {
			return true;
		}
		return false;
	}

	public String getID() {
		return investment.id;
	}
	
	public String getType() {
		return investment.type;
	}
	
	public double getPrice() {

		return ((Stock) investment).getPrice();

	}
	
	public String getName() {
		if (investment instanceof Stock) {
			return ((Stock) investment).getName();
		}
		else {
			return ((Bond) investment).getName();
		}
		
	}
	
	public Instant getNow() {
		return now;
	}
	
	public Instant getDue() {
		return due;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getInterest() {
		return interest;
	}
}
