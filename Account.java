import java.util.ArrayList;
import java.util.Date;

public class Account {
	protected String Aid;
	protected ArrayList<Transaction> transactions;
	protected double money;
	protected double interestRate;
	protected AccountType type;
	
	Account(String id, AccountType t){
		money = 0;
		Aid = id;
		t = type;
	}
	
	Account(String id, AccountType t, double m) {
		money = m;
		Aid = id;
		t = type;
	}
	
	//account saves money m
	
	public boolean save(double m) {
		if(m <= 0.0) {
			return false;
		}
		money = money + m;
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.SAVE, Aid, m, date.toString()));
		return true;
	}
	
	// account withdraws money m
	public boolean withdraw(double m) {
		if(money >= m) {
			money = money - m;
			Date date = new Date();
			transactions.add(new Transaction(TransactionType.WITHDRAW, Aid, m, date.toString()));
			return true;
		}
		return false;
	}
	
	// account borrows money m from bank
	public boolean loan(double m) {
		money = money - mortgage(m);
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.LOAN, Aid, m, date.toString()));
		return true;
	}
	
	// account receives money m from sender
	public boolean receive(double m, String sender) {
		money = money + m;
		Date date = new Date();
		transactions.add(new Transaction(TransactionType.RECEIVE, sender, Aid, m, date.toString()));
		return true;
	}
	
	// account sends money m to receiver
	public boolean send(double m, String receiver) {
		if(money >= m) {
			money = money - m;
			Date date = new Date();
			transactions.add(new Transaction(TransactionType.SEND, Aid, receiver, m, date.toString()));
			return true;
		}
		return false;
	}
	
	protected double mortgage(double m) {
		
		return m * 1.2;
	}
	
	public double getMoney() {
		return money;
	}
	
	public AccountType accountType() {
		return type;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Transaction t : transactions) {
			sb.append(t);
		}
		return sb.toString();
	}

}
