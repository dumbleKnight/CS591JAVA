package backend;

import java.util.Date;
import java.util.HashMap;

public class SecurityAccount extends Account {
	private HashMap<String, Property> property; //<key : sid, value : property>
	
	SecurityAccount(String id, double m) {
		super(id, AccountType.Security, m);
	}
	
	public boolean buyBond(double Money, Investment i, Property.InterestRate irate, double interest) {
		if(money < Money || Money <= 0.0 || property.containsKey(i.getId())) {
			return false;
		}
		money = money - Money;
		property.put(i.getId(), new Property(Money, i, irate, interest));
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.BUY, Aid, i.getId(), Money, Money, date.toString()));
		return true;		
	}
	
	public boolean buyStock(Investment i, int amount, double price) {
		double expense = (double)amount * price;
		if(amount <= 0 || expense <= 0.0 || money < expense) {
			return false;
		} 
		money = money - expense;
		
		Property temp;
		if(property.containsKey(i.getId())) {
		   temp = property.get(i.getId());
		   temp.add((double) amount);
		}else {
		   temp = new Property((double) amount, i);
		}
		property.put(i.getId(), temp);
		
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.BUY, Aid, i.getId(), expense, (double)amount, date.toString()));
		return true;
	}
	
	public boolean sellBond(String sid) {
		if(!property.containsKey(sid)) {
			return false;
		}
		
		double amount = property.get(sid).getAmount();
		double profit = property.get(sid).sell();
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.SELL, Aid, sid, profit, amount, date.toString()));
		money = money + profit;
		property.remove(sid);
		
		return true;		
	}
	
	public boolean sellStock(String sid, double amount, double price) {
		if(!property.containsKey(sid) || amount > property.get(sid).getAmount() || price <= 0.0) {
			return false;
		}
		Property temp = property.get(sid);
		double profit = temp.sell(amount, price);
		property.put(sid, temp);
		
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.SELL, Aid, sid, profit, amount, date.toString()));
		money = money + profit;
		
		if(property.get(sid).empty()) {
			property.remove(sid);
		}
		return true;
	}
	
	
	

}
