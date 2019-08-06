import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class User {
	static private int count; //not sure how to use this 
	private String Uid;
	private String password;
	private String name;
	private String[] aids;
	private HashMap<String, Account> accounts;
	private static String transPath = "/Users/kangtungho/Desktop/transaction.json";

	
	//uid->aids mapping JSON file
	
	User(String uid, String p, String n){
		count = 0;
		Uid = uid;
		password = p;
		name = n;
		accounts = new HashMap<String, Account>();
		//might need a list of aid that user has 
		//accounts_init()
	}
	
	User(String uid, String p, String n, String[] aids){
		count = 0;
		Uid = uid;
		password = p;
		name = n;
		this.aids = aids;
		accounts = new HashMap<String, Account>();
		//might need a list of aid that user has 
		//accounts_init()
	}
	
	public void parseUserAccount(JSONObject account) {
		Account temp;
		
		//Get account id
        String aid = (String) account.get("aid");
        System.out.println("aid is: " + aid);
        
        //Get user password
        String type = (String) account.get("type");
        System.out.println("type is: " + type);
        
        double interest = (double) account.get("interest");
        System.out.println("interest rate is: " + interest);
        
        //Get user name
        double money = (double) account.get("money");
        System.out.println("money is: " + money);
        
        
        
        if (type.equals("checking")) {
        	temp = new CheckingAccount(aid, AccountType.Checking, money);
        }
        else if (type.equals("saving")) {
        	temp = new SavingAccount(aid, AccountType.Saving, money);
        }
        else {
        	temp = new SecurityAccount(aid, AccountType.Security, money);
        }
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(transPath)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray transList = (JSONArray) obj;
            //System.out.println(userList);
             
            //Iterate over employee array
            //userList.forEach( usr -> parseUserObject( (JSONObject) usr ) );
            for (int i = 0; i < transList.size(); i++) {
            	JSONObject transObj = (JSONObject)transList.get(i);
            	if (transObj.containsKey(aid)) {
            		temp.parseUserTrans(transObj);
            		accounts.put(aid, temp);
            		break;
            	}
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
    }
	
	private void store_data() {
		
	}
	
	private void accounts_init() {
		//initialize user account data retried from DB
		//first check for if data base is empty
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
