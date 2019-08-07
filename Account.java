import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Account {
    protected String Aid;
	protected ArrayList<Transaction> transactions;
	protected double money;
	protected double interestRate; //this shouldn't be in account, bank should have it
	protected AccountType type;
	
	
	Account(String id, AccountType t){
		money = 0;
		Aid = id;
		t = type;
		transactions = new ArrayList<Transaction>();
		//transactions_init()
	}
	
	Account(String id, AccountType t, double m) {
		money = m;
		Aid = id;
		t = type;
		transactions = new ArrayList<Transaction>();
		//transactions_init()
	}
	
	public void parseUserTrans(JSONObject trans) {
    	JSONArray transList = (JSONArray) trans.get(Aid);
    	Transaction tran;
    	String sender;
    	String receiver;
    	double money;
    	double amount;
    	String date;
    	String InvestmentId;
    	
    	for (int i = 0; i < transList.size(); i++) {
    		JSONObject obj = (JSONObject) transList.get(i);
    		String type = (String) obj.get("type");
    		// Withdraw, loan, save
    		if (type.equals("Withdraw") || type.equals("Loan") || type.equals("Save")) {
    			sender = (String) obj.get("sender");
    			money = (double) obj.get("money");
    			date = (String) obj.get("date");
    			if (type.equals("Withdraw")) {
    				tran = new Transaction(TransactionType.WITHDRAW,sender,money,date);
    			}
    			else if (type.equals("Loan")) {
    				tran = new Transaction(TransactionType.LOAN,sender,money,date);
    			}
    			else {
    				tran = new Transaction(TransactionType.SAVE,sender,money,date);
    			}
    			
    		}
    		// Send, Receive 
    		else if (type.equals("Send")||type.equals("Receive")) {
    			sender = (String) obj.get("sender");
    			receiver = (String) obj.get("receiver");
    			money = (double) obj.get("money");
    			date = (String) obj.get("date");
    			if (type.equals("Send")) {
        			tran = new Transaction(TransactionType.SEND, sender, receiver, money, date);
    			}
    			else {
        			tran = new Transaction(TransactionType.RECEIVE, sender, receiver, money, date);

    			}
    		}
    		// Buy, Sell
    		else {
    			sender = (String) obj.get("sender");
    			money = (double) obj.get("money");
    			date = (String) obj.get("date");
    			amount = (double) obj.get("amount");
    			InvestmentId = (String) obj.get("investmentId");
    			if (type.equals("Buy")) {
        			tran = new Transaction(TransactionType.BUY, sender, InvestmentId, money, amount, date);

    			}
    			else {
        			tran = new Transaction(TransactionType.SELL, sender, InvestmentId, money, amount, date);

    			}
    		}
    		transactions.add(tran);
    	}
    }
	

	
	//account saves money m
	
	public boolean save(double m) {
		if(m <= 0.0) {
			return false;
		}
		money = money + m;
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.SAVE, Aid, m, parseInstant(now)));
		return true;
	}
	
	// account withdraws money m
	public boolean withdraw(double m) {
		if(money >= m) {
			money = money - m;
			Instant now = Instant.now();
			transactions.add(new Transaction(TransactionType.WITHDRAW, Aid, m, parseInstant(now)));
			return true;
		}
		return false;
	}
	
	// account borrows money m from bank
	public boolean loan(double m) {
		money = money - mortgage(m);
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.LOAN, Aid, m, parseInstant(now)));
		return true;
	}
	
	// account receives money m from sender
	public boolean receive(double m, String sender) {
		money = money + m;
		Instant now = Instant.now(); 
		transactions.add(new Transaction(TransactionType.RECEIVE, sender, Aid, m, parseInstant(now)));
		return true;
	}
	
	// account sends money m to receiver
	public boolean send(double m, String receiver) {
		if(money >= m) {
			money = money - m;
			Instant now = Instant.now();
			transactions.add(new Transaction(TransactionType.SEND, Aid, receiver, m, parseInstant(now)));
			return true;
		}
		return false;
	}
	
	private String parseInstant(Instant time) {
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Locale.US )
			                     .withZone( ZoneId.systemDefault() );
		String output = formatter.format( time );
		return output;
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
