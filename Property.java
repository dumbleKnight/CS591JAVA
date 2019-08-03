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
	
	Property(double a, Investment i) {
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
				due = now.plus(Duration.ofDays(7));
				break;
			
			case MONTH:
				due = now.plus(Duration.ofDays(30));
				break;
			
			default:
				due = now.plus(Duration.ofDays(365));
		}
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

	public double getAmount() {
		return amount;
	}
}
