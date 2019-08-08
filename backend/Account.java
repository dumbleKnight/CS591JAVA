package backend;

import java.io.FileWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Account {
	protected String Aid;
	protected ArrayList<Transaction> transactions;
	protected double money;
	protected double interestRate;
	protected AccountType type;
	
	Account(String id, AccountType t){
		money = 0;
		Aid = id;
		type = t;
		transactions = new ArrayList<Transaction>();
	}
	
	Account(String id, AccountType t, double m) {
		money = m;
		Aid = id;
		type = t;
		transactions = new ArrayList<Transaction>();
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
	
	public void storeTrans(JSONArray finalJSON) {
		JSONObject aidObj = new JSONObject();
		JSONArray transList = new JSONArray();
		for (Transaction tran : transactions) { 
			JSONObject tranObj = new JSONObject();
			TransactionType tranType = tran.type;
			if (tranType == TransactionType.WITHDRAW || tranType == TransactionType.LOAN || tranType == TransactionType.SAVE) {
				// Withdraw, loan, save
				String sender = tran.sender;
				double money = tran.money;
				String date = tran.date;
				switch (tranType) {
				case WITHDRAW:
					tranObj.put("type", "Withdraw");
					break;
				case LOAN:
					tranObj.put("type", "Loan");
					break;
				case SAVE:
					tranObj.put("type", "Save");
					break;	
				}
				tranObj.put("sender", sender);
				tranObj.put("money", money);
				tranObj.put("date", date);
				
			}
			else if (tranType == TransactionType.SEND || tranType == TransactionType.RECEIVE) {
				// Send, Receive 
				String sender = tran.sender;
				String receiver = tran.receiver;
				double money = tran.money;
				String date = tran.date;
				switch (tranType) {
				case SEND:
					tranObj.put("type", "Send");
					break;
				case RECEIVE:
					tranObj.put("type", "Receive");
					break;
				}
				tranObj.put("sender", sender);
				tranObj.put("receiver", receiver);
				tranObj.put("money", money);
				tranObj.put("date", date);
			}
			else {
				// Buy, Sell
				String sender = tran.sender;
				String investmentId = tran.investmentId;
				double money = tran.money;
				double amount = tran.money;
				String date = tran.date;
				switch (tranType) {
				case BUY:
					tranObj.put("type", "Buy");
					break;
				case SELL:
					tranObj.put("type", "Sell");
					break;
				}
				tranObj.put("sender", sender);
				tranObj.put("investmentId", investmentId);
				tranObj.put("money", money);
				tranObj.put("amount", amount);
				tranObj.put("date", date);
			
			}
			transList.add(tranObj);
	      }
		aidObj.put(Aid,transList);
		finalJSON.add(aidObj);
		
		
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
			sb.append(" | ");
		}
		return sb.toString();
	}

}
