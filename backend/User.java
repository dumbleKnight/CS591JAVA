import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class User {
	static private int count;
	private String Uid;
	private String password;
	private String name;
	private HashMap<String, Account> accounts;
	
	User(String uid, String p, String n){
		count = 0;
		Uid = uid;
		password = p;
		name = n;
		accounts = new HashMap<String, Account>();
	}
		
	private String generateAid() {
		StringBuilder sb = new StringBuilder();
		sb.append(Uid + "-");
		sb.append(count++);
		return sb.toString();
	}
	
	public boolean logIn(String p) {
		if(p != password) {
			return false;
		}
		return true;
	}
	
	public String getUid() {
		return Uid;
	}
	
	public String createCheckingAccount() {
		String aid = generateAid();
		CheckingAccount account = new CheckingAccount(aid, AccountType.Checking);
		accounts.put(aid, account);
		return aid;
	}
	
	public String createCheckingAccount(double money) {
		String aid = generateAid();
		if(money < 0.0) {
			return null;
		}
		Account account = new CheckingAccount(aid, AccountType.Checking, money);
		accounts.put(aid, account);
		return aid;
	}
	
	public String createSavingAccount() {
		String aid = generateAid();
		Account account = new SavingAccount(aid, AccountType.Saving);
		accounts.put(aid, account);
		return aid;
	}
	
	public String createSavingAccount(double money) {
		String aid = generateAid();
		if(money < 0.0) {
			return null;
		}
		Account account = new SavingAccount(aid, AccountType.Saving, money);
		accounts.put(aid, account);
		return aid;
	}

	public String createSecurity(double money) {
		if(money < 5000) {
			return null;
		}
		String aid = generateAid();
		Account account = new SecurityAccount(aid, AccountType.Security, money);
		accounts.put(aid, account);
		return aid;
	}

	public ArrayList<String> getAccountId() {
		ArrayList<String> res = new ArrayList<String>();
		for(String Aid : accounts.keySet()) {
			res.add(Aid);
		}
		return res;
	}
	
	public Account getAccount(String Aid) { 
		if(accounts.containsKey(Aid)) {
			return accounts.get(Aid);
		}
		return null;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, Account> entry : accounts.entrySet()) {
			sb.append(entry.getValue());
		}
		return sb.toString();
	}
	
	public boolean saveMoney(String sender, double m) {
		if(!accounts.containsKey(sender)) {
			return false;
		}
		
		Account temp = accounts.get(sender);
		if(temp.save(m)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;		
	}
	
	public boolean loanMoney(String sender, double m) {
		if(!accounts.containsKey(sender)) {
			return false;
		}
		Account temp = accounts.get(sender);
		temp.loan(m);
		accounts.put(sender, temp);
		return true;
	}
	
	public boolean sendMoney(double m, String receiver, String sender) {
		if(!accounts.containsKey(sender)) {
			return false;
		}
		Account temp = accounts.get(sender);
		if(temp.send(m, receiver)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;
	}
	
	public boolean receiveMoney(double m, String receiver, String sender) {
		if(!accounts.containsKey(receiver)) {
			return false;
		}
		Account temp = accounts.get(receiver);
		temp.receive(m, sender);
		accounts.put(receiver, temp);
		return true;
	}
	
	public boolean withdrawMoney(double m, String sender) {
		if(!accounts.containsKey(sender)) {
			return false;
		}
		Account temp = accounts.get(sender);
		if(temp.withdraw(m)) {
			temp.withdraw(m);
			accounts.put(sender, temp);
			return true;
		}
		return false;
	}

	public boolean buyStock(Investment i, int amount, double price, String sender) {
		if(!accounts.containsKey(sender) && accounts.get(sender).accountType() == AccountType.Security) {
			return false;
		}
		SecurityAccount temp = (SecurityAccount) accounts.get(sender);
		
		if(temp.buyStock(i, amount, price)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;
	}
	
	public boolean buyBond(double Money, Investment i, Property.InterestRate irate, double interest, String sender) {
		if(!accounts.containsKey(sender) && accounts.get(sender).accountType() == AccountType.Security ) {
			return false;
		}
		SecurityAccount temp = (SecurityAccount) accounts.get(sender);
		
		if(temp.buyBond(Money, i, irate, interest)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;		
	}

	public boolean sellBond(String sender, String sid) {
		if(!accounts.containsKey(sender) && accounts.get(sender).accountType() == AccountType.Security ) {
			return false;
		}
		SecurityAccount temp = (SecurityAccount) accounts.get(sender);
		
		if(temp.sellBond(sid)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;
	}
	
	public boolean sellStock(String sender, String sid, double amount, double price) {
		if(!accounts.containsKey(sender) && accounts.get(sender).accountType() == AccountType.Security ) {
			return false;
		}
		SecurityAccount temp = (SecurityAccount) accounts.get(sender);
		
		if(temp.sellStock(sid, amount, price)) {
			accounts.put(sender, temp);
			return true;
		}
		return false;
	}

	public boolean closeAccount(String sender) {
		if(accounts.containsKey(sender)) {
			accounts.remove(sender);
			return true;
		}
		return false;
	}

}
