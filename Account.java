import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Account {
    protected String Aid;
	protected ArrayList<Transaction> transactions;
	protected double money;
	protected double interestRate; //this shouldn't be in account, bank should have it
	protected AccountType type;
	
	/*
	 * About/Structure of transaction.json
	 * 1st key should be aid
	 * once indexed through aid, object inside is the transaction
	 * each transaction has field of:
	 * public TransactionType type;
       public String sender ; // uid
       public String receiver ; // uid
       public double money;
       public String investmentId;
       public double amount;
       public String date;
	 */
	
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
	
	private void transactions_init() {
		//The Json file should store the aID as the first parameter
		//initialize accounts that are stored in DB
		//first check for if data base is empty
		//while loop the objects in the JSON file
		//In each iteration
		//1. create new Transaction
		//2. check for transaction type ( 1. send/recieve, 2. sell/buy, 3. withdraw/loan/save
		//3. fill in the appropriate fields according to trans type
		//4. add to transaction arraylist
		
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
