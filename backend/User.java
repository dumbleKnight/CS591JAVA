package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

public class User {
	static private int count;
	private String Uid;
	private String password;
	private ArrayList<String> aids;
	private String name;
	private HashMap<String, Account> accounts;
	private static String transPath = "src/backend/transaction.json";

	User(String uid, String p, String n){
		count = 0;
		Uid = uid;
		password = p;
		aids = new ArrayList<String>();
		name = n;
		accounts = new HashMap<String, Account>();
	}
	
	User(String uid, String p, String n, ArrayList<String> aids){
		count = aids.size();
		Uid = uid;
		password = p;
		name = n;
		this.aids = aids;
		accounts = new HashMap<String, Account>();
		//might need a list of aid that user has 
		//accounts_init()
	}
	
	public void printAccounts() {
		for (Account acc : accounts.values()) {
			System.out.println(acc.type.equals(AccountType.Checking));
		}
	}
	
	public void parseUserAccount(JSONObject account) {
		Account temp;
		
		//Get account id
        String aid = (String) account.get("aid");
        
        //Get user password
        String type = (String) account.get("type");
        
        double interest = (double) account.get("interest");
        
        //Get user name
        double money = (double) account.get("money");
        
        
//        System.out.println("aid is: " + aid);
//        System.out.println("type is: " + type);
//        System.out.println("interest rate is: " + interest);
//        System.out.println("money is: " + money);
        
        if (type.equals("checking")) {
        	temp = new CheckingAccount(aid,money);
        }
        else if (type.equals("saving")) {
        	temp = new SavingAccount(aid, money);
        }
        else  {
        	temp = new SecurityAccount(aid, money);
        	((SecurityAccount) temp).parseProperty();
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
            		break;
            	}
            }
            accounts.put(aid, temp);
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
    }
	
	public void storeAccount(JSONArray finalJSON) {
		String type;
		for (Account acc : accounts.values()) {
			System.out.println(acc.Aid);
			JSONObject jsonObject = new JSONObject();
			
			String aid = acc.Aid;
			jsonObject.put("aid", aid);
			double money = acc.money;
			jsonObject.put("money", money);
			double interest = acc.interestRate;
			jsonObject.put("interest", interest);
			AccountType accType = acc.type;
			if (accType==AccountType.Saving) {
				type = "saving";
			}
			else if (accType == AccountType.Checking) {
				type = "checking";
			}
			else {
				type = "security";
			}
			jsonObject.put("type", type);
			finalJSON.add(jsonObject);
		}
		 
	}
	
	public void toAccount(JSONArray finalJSON) {
		for (Account acc : accounts.values()) {
			acc.storeTrans(finalJSON);
		}
	}
	
	public void toSecurity(JSONArray finalJSON) {
		for (Account acc: accounts.values()) {
			//System.out.println(acc.type);
			if (acc.type==AccountType.Security) {
				((SecurityAccount) acc).storeProperty(finalJSON);
			}
		}
		
	}
	
	private String generateAid() {
		StringBuilder sb = new StringBuilder();
		sb.append(Uid + "-");
		sb.append(count++);
		return sb.toString();
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<String> getAids() {
		return aids;
	}
	
	public boolean logIn(String p) {
		if(!p.equals(password)) {
			return false;
		}
		return true;
	}
	
	public String getUid() {
		return Uid;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String createCheckingAccount() {
		String aid = generateAid();
		CheckingAccount account = new CheckingAccount(aid);
		accounts.put(aid, account);
		
		aids.add(aid);
		return aid;
	}
	
	public String createCheckingAccount(double money) {
		String aid = generateAid();
		if(money < 0.0) {
			return null;
		}
		Account account = new CheckingAccount(aid, money);
		accounts.put(aid, account);
		
		aids.add(aid);
		return aid;
	}
	
	public String createSavingAccount() {
		String aid = generateAid();
		Account account = new SavingAccount(aid);
		accounts.put(aid, account);
		
		aids.add(aid);
		return aid;
	}
	
	public String createSavingAccount(double money) {
		String aid = generateAid();
		if(money < 0.0) {
			return null;
		}
		Account account = new SavingAccount(aid, money);
		accounts.put(aid, account);
		
		aids.add(aid);
		return aid;
	}

	public String createSecurity(double money) 
	
	{
		if(money < 5000) {
			return null;
		}
		String aid = generateAid();
		Account account = new SecurityAccount(aid, money);
		accounts.put(aid, account);
		
		aids.add(aid);
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
		double p = ((Stock)i).getPrice() ;
		if(temp.buyStock(i, amount, p)) {
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
