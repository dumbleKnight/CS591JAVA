package backend;

import java.util.HashMap;
import java.util.Map.Entry;

public class Bank {
	private HashMap<String, User> users;
	private HashMap<String, Investment> investments;
	private static int userCount;
	private static int investmentCount;
	
	public Bank() {
		userCount = 0;
		investmentCount = 0;
		users = new HashMap<String, User>();
		investments = new HashMap<String, Investment>();
	}
	
	private String generateUserId() {
		StringBuilder sb = new StringBuilder();
		sb.append(userCount++);
		return sb.toString();
	}
	
	private String getUserID(String Aid) {
		String[] parts = Aid.split("-");
		if(parts.length != 2) {
			return null;
		}
		return parts[0];
	}
	
	private String generateInvestmentId() {
		StringBuilder sb = new StringBuilder();
		sb.append(investmentCount++);
		return sb.toString();
	}
	
	public boolean addStock(double p, String n) {
		if(p <= 0.0) {
			return false;
		}
		String sid = generateInvestmentId();
		Stock temp = new Stock(sid, p, n);
		investments.put(sid, temp);
		return true;
	}
	
	public boolean addBond() {
		String sid = generateInvestmentId();
		Bond temp = new Bond(sid);
		investments.put(sid, temp);
		return true;
	}
	
	public boolean addBond(double d1, double d2, double d3) {
		if(d1 <= 0.0 || d2 <= 0.0 || d3 <= 0.0) {
			return false;
		}
		String sid = generateInvestmentId();
		Bond temp = new Bond(sid, d1, d2, d3);
		investments.put(sid, temp);
		return true;
	}

	public String createUser(String name, String password) {
		String uid = generateUserId();
		User temp = new User(uid, password, name);
		
		users.put(uid, temp);
		return uid;
	}
	
	public boolean logIn(String Uid, String password) {
		if(!users.containsKey(Uid)) {
			return false;
		}
		return users.get(Uid).logIn(password);
	}

	public String createCheckingAccount(String Uid) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		User u = users.get(Uid);
		String Aid = u.createCheckingAccount();
		users.put(Uid, u);
		return Aid;
	}
	
	public String createCheckingAccount(String Uid, double money) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		User u = users.get(Uid);
		String Aid = u.createCheckingAccount(money);
		if(Aid != null) {
			users.put(Uid, u);
		}
		return Aid;
	}
	
	public String createSavingAccount(String Uid) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		User u = users.get(Uid);
		String Aid = u.createSavingAccount();
		users.put(Uid, u);
		return Aid;
	}
	
	public String createSavingAccount(String Uid, double money) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		
		User u = users.get(Uid);
		String Aid = u.createSavingAccount(money);
		if(Aid != null) {
			users.put(Uid, u);
		}
		return Aid;
	}
	
	public String createSecurityAccount(String Uid,double money) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		
		User u = users.get(Uid);
		String Aid = u.createSecurity(money);
		if(Aid != null) {
			users.put(Uid, u);
		}
		return Aid;
	}
	
	public boolean close(String Uid) {
		if(users.containsKey(Uid)) {
			users.remove(Uid);
			return true;
		}
		return false;
	}

	public User getUser(String Uid) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		return users.get(Uid);
	}
	
	public boolean saveMoney(String Aid, double money) {
		String uid = getUserID(Aid);
		if(uid == null || !users.containsKey(uid)) {
			return false;
		}
		
		User u = users.get(uid);
		if(u.saveMoney(Aid, money)) {
			users.put(uid, u);
			return true;
		}
		return false;
	}
	
	public boolean withdrawMoney(String Aid, double money) {
		String uid = getUserID(Aid);
		if(uid == null || !users.containsKey(uid)) {
			return false;
		}
		
		User u = users.get(uid);
		if(u.withdrawMoney(money, Aid)) {
			users.put(uid, u);
			return true;
		}
		return false;
	}
	
	public boolean loanMoney(String Aid, double money) {
		String uid = getUserID(Aid);
		if(uid == null || !users.containsKey(uid)) {
			return false;
		}
		
		User u = users.get(uid);
		if(u.loanMoney(Aid, money)) {
			users.put(uid, u);
			return true;
		}
		return false;
	}
	
	public boolean sendMoney(double m, String receiver, String sender) {
		String senderUid = getUserID(sender);
		String receiverUid = getUserID(receiver);
		if(senderUid == null || receiverUid == null || !users.containsKey(senderUid) || !users.containsKey(receiverUid)) {
			return false;
		}
		
		User S = users.get(senderUid);
		User R = users.get(receiverUid);
		
		if(S.sendMoney(m, receiver, sender) && R.receiveMoney(m, receiver, sender)) { 
			users.put(senderUid, S);
			users.put(receiverUid, R);
			return true;
		}
		return false;
	}
	
	public boolean buyBond(double Money, String sid, Property.InterestRate irate, double interest, String sender) {
		String uid = getUserID(sender);
		if(uid == null || !users.containsKey(uid) || !investments.containsKey(sid)) {
			return false;
		}
		
		Investment i = investments.get(sid);
		User u = users.get(uid);
		
		if(i instanceof Bond) {
			if(u.buyBond(Money, i, irate, interest, sender)) {
				users.put(uid, u);
				return true;
			}
			return false;
		}
		return false;
	}
	
	public boolean buyStock(String sid, int amount, double price, String sender) {
		String uid = getUserID(sender);
		if(uid == null || !users.containsKey(uid) || !investments.containsKey(sid)) {
			return false;
		}
		
		Investment i = investments.get(sid);
		User u = users.get(uid);
		
		if(i instanceof Stock && ((Stock) i).getPrice() <= price) {
			if(u.buyStock(i, amount, price, sender)) {
				users.put(uid, u);
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean sellBond(String sender, String sid) {
		String uid = getUserID(sender);
		if(uid == null || !users.containsKey(uid) || !investments.containsKey(sid)) {
			return false;
		}
		
		Investment i = investments.get(sid);
		User u = users.get(uid);
		
		if(i instanceof Bond) {
			if(u.sellBond(sender, sid)) {
				users.put(uid, u);
				return true;
			}
		}
		return false;		
	}
	
	public boolean sellStock(String sender, String sid, double amount, double price) {
		String uid = getUserID(sender);
		if(uid == null || !users.containsKey(uid) || !investments.containsKey(sid)) {
			return false;
		}
		
		Investment i = investments.get(sid);
		User u = users.get(uid);
		
		if(i instanceof Stock && ((Stock) i).getPrice() > price) {
			if(u.sellStock(sender, sid, amount, price)) {
				users.put(uid, u);
				return true;
			}
			return false;
		}		
		return false;		
	}
	
	public String getInvestmentInfo() {
		StringBuilder sb = new StringBuilder();
		for(Entry<String, Investment> e : investments.entrySet()) {
			sb.append(e.getValue());
		}
		return sb.toString();
	}
	
	static public void main(String[] args) { 
		String test = "1-1";
		String[] parts = test.split("-");
		System.out.println(parts[0]);
	}
	
}
