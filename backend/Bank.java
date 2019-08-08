package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Map.Entry;
import java.util.Set;

public class Bank {
	private HashMap<String, User> users;
	private HashMap<String, Investment> investments;
	private static int userCount;
	private static int investmentCount;
	static String userPath = "src/backend/user.json";
	static String accountPath = "src/backend/account.json";
	static String investPath = "src/backend/investments.json";
	private static String transPath = "src/backend/transaction.json";
	private static String propertyPath = "src/backend/property.json";
	
	public Bank() {
		userCount = 0;
		investmentCount = 0;
		users = new HashMap<String, User>();
		investments = new HashMap<String, Investment>();
		
		investments_init();
		user_init();
		
//		addStock(10.0, "Google", "GOOGL");
//		addStock(10.0, "Apple", "AAPL");
//		addStock(10.0, "Microsoft", "MSFT");
//		
//		addBond();
//		addBond();
//		addBond();
	}
	
	public void changePrice() {
		for (Investment invest: investments.values()) {
			if (invest instanceof Stock) {
				((Stock)invest).change();
			}
		}
	}
	
	private void investments_init() {
		//initialize stocks and bonds using data from DB
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(investPath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray investList = (JSONArray) obj;
            //System.out.println(userList);
             
            //Iterate over employee array
            //userList.forEach( usr -> parseUserObject( (JSONObject) usr ) );
            for (int i = 0; i < 3; i++) {
            	parseBondObject((JSONObject)investList.get(i));
            }
            for (int i = 3; i < investList.size(); i++) {
            	parseStockObject((JSONObject) investList.get(i));
            }
            investmentCount = investList.size();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	private void parseBondObject(JSONObject bond) {
		String investmentId = (String) bond.get("investmentId");
		System.out.println(investmentId);
		
		String name = (String) bond.get("name");
		System.out.println(name);
		
		double interest = (double) bond.get("interest");
		System.out.println("interest is " + interest);
		
		
		Bond temp = new Bond(investmentId, interest, name);
		
		investments.put(investmentId, temp);
		
	}
	
	private void parseStockObject(JSONObject stock) {
		String investmentId = (String) stock.get("investmentId");
		System.out.println(investmentId);
		
		double price = (double) stock.get("price");
		System.out.println(price);
			
		String name = (String) stock.get("name");
		System.out.println(name);
		
		Stock temp = new Stock(investmentId, price, name);
		
		investments.put(investmentId, temp);
		
		
		
	}
	
	private void user_init() {
		//initialize existing users from DB
		//first check for if data base is empty
		//1. fill in user data (new User)
		//2. once we have the aid list, fill up the account info (new Account)
		//3. create new Transaction based on the JSON mapping
		//4. add transcations to the assotiated account
		//5. fill in the hashmap for user aid->account mapping
		//6. repeat until every user JSON file is finished
		
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(userPath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray userList = (JSONArray) obj;
            //System.out.println(userList);
             
            //Iterate over employee array
            //userList.forEach( usr -> parseUserObject( (JSONObject) usr ) );
            for (int i = 0; i < userList.size(); i++) {
            	parseUserObject((JSONObject)userList.get(i), jsonParser);
            }
            userCount = userList.size();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public static boolean contains(ArrayList<String> aid_array, String targetValue) {
	    for (String s: aid_array) {
	        if (s.equals(targetValue))
	            return true;
	    }
	    return false;
	}
	
	private void parseUserObject(JSONObject user, JSONParser parser)
    {
		String aid;
        //Get user id
        String uid = (String) user.get("uid");
        
        
        //Get user password
        String password = (String) user.get("password");
        
         
        //Get user name
        String name = (String) user.get("name");
        
        
        JSONArray js_array = (JSONArray) user.get("aids");
        ArrayList<String> aid_array = new ArrayList<String>();
        for (int i = 0; i < js_array.size(); i++) {
        	aid_array.add(String.valueOf(js_array.get(i)));
        }
        
//        System.out.println("uid is: " + uid);
//        System.out.println("password is: " + password);
//        System.out.println("name is: " + name);
//        System.out.println(Arrays.toString(aid_array));
        
        User temp = new User(uid, password, name, aid_array);
        
        //Fill in the hashmap for aid->account mapping
        try (FileReader reader = new FileReader(accountPath))
        {
            //Read JSON file
            Object obj = parser.parse(reader);
 
            JSONArray accountList = (JSONArray) obj;
            //System.out.println(userList);
            JSONObject accountObj;
            //Iterate over employee array
            //userList.forEach( usr -> parseUserObject( (JSONObject) usr ) );
            for (int i = 0; i < accountList.size(); i++) {
            	//each user object from the previous loop will loop through the Account Json file once
            	//initializes variables inside each user object and add it to the uid->user hashmap
            	accountObj = (JSONObject) accountList.get(i);
            	aid = (String) accountObj.get("aid");
            	if (contains(aid_array, aid)) {
            		temp.parseUserAccount(accountObj);
            	}
            	
            }
            users.put(uid, temp);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
	

	public void writeUser() {
		JSONArray finalJSON = new JSONArray();
		for (User user : users.values()) {
			JSONObject jsonObject = new JSONObject();
		
		    String uid = user.getUid();
		    jsonObject.put("uid", uid);
		    String password = user.getPassword();
		    jsonObject.put("password", password);
		    String name = user.getName();
		    jsonObject.put("name", name);
		    ArrayList<String> aids = user.getAids();
		    JSONArray jsonArray = new JSONArray();
		    for (String s: aids) {
		    	jsonArray.add(s);
		    }
		    jsonObject.put("aids", jsonArray);
		    finalJSON.add(jsonObject);
		    
		    
		}
		 try
	        {
	            
	            // Create a new FileWriter object
	            FileWriter fileWriter = new FileWriter(userPath);

	            // Writting the jsonObject into sample.json
	            fileWriter.write(finalJSON.toJSONString());
	            fileWriter.close();

	            System.out.println("JSON Object Successfully written to the file!!");

	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	}
	
	public void writeAccount() {
		JSONArray finalJSON = new JSONArray();
		for (User user : users.values()) {
			System.out.println(user.getName());
			user.storeAccount(finalJSON);
		}
		try
        {
            // Create a new FileWriter object
            FileWriter fileWriter = new FileWriter(accountPath);

            // Writting the jsonObject into sample.json
            fileWriter.write(finalJSON.toJSONString());
            fileWriter.close();

            System.out.println("JSON Object Successfully written to the file!!");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void writeTransaction() {
		JSONArray finalJSON = new JSONArray();
		for (User user : users.values()) {
			user.toAccount(finalJSON);
		}
		try
        {
            
            // Create a new FileWriter object
            FileWriter fileWriter = new FileWriter(transPath);

            // Writting the jsonObject into sample.json
            fileWriter.write(finalJSON.toJSONString());
            fileWriter.close();

            System.out.println("JSON Object Successfully written to the file!!");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void writeProperty() {
		JSONArray finalJSON = new JSONArray();
		for (User user : users.values()) {
			user.toSecurity(finalJSON);
		}
		try
        {
            
            // Create a new FileWriter object
            FileWriter fileWriter = new FileWriter(propertyPath);

            // Writting the jsonObject into sample.json
            fileWriter.write(finalJSON.toJSONString());
            fileWriter.close();

            System.out.println("JSON Object Successfully written to the file!!");

        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
    
    public void getUserCount() {
    	System.out.println(userCount);
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
	
	public boolean addStock(double p, String n, String t) {
		if(p <= 0.0) { 
			return false;
		}
		String sid = generateInvestmentId();
		Stock temp = new Stock(sid, p, n, t);
		investments.put(sid, temp);
		return true;
	}
	
//	public boolean addBond() {
//		String sid = generateInvestmentId();
//		Bond temp = new Bond(sid);
//		investments.put(sid, temp);
//		return true;
//	}
	
	public boolean addBond(double interest, double price, String name) {
//		if(d1 <= 0.0 || d2 <= 0.0 || d3 <= 0.0) {
//			return false;
//		}
		String sid = generateInvestmentId();
		Bond temp = new Bond(sid, interest, name);
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
	
	public boolean closeAccount(String Aid) {
		String uid = getUserID(Aid);
		if(uid == null || !users.containsKey(uid)) {
			return false;
		}
		
		User u = users.get(uid);
		u.closeAccount(Aid);
		return true;
	}

	public User getUser(String Uid) {
		if(!users.containsKey(Uid)) {
			return null;
		}
		return users.get(Uid);
	}
	
	public Set<String> getUserList() {
		return users.keySet();
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
		
		if(i instanceof Stock) {
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
		
		if(i instanceof Stock) {
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
			//System.out.println(e.getValue());
			sb.append(e.getValue());
			sb.append("|");
		}
		return sb.toString();
	}
	
	static public void main(String[] args) { 
		String test = "1-1";
		String[] parts = test.split("-");
		System.out.println(parts[0]);
	}
	
}
