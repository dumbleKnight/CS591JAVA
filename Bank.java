import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Bank {
	private HashMap<String, User> users;
	private HashMap<String, Investment> investments;
	private static int userCount;
	private static int investmentCount;
	static String userPath = "/Users/kangtungho/Desktop/user.json";
	static String accountPath = "/Users/kangtungho/Desktop/account.json";
<<<<<<< HEAD
	static String investPath = "/Users/kangtungho/Desktop/investments.json";
=======
	static String investPath = "/Users/kangtungho/Desktop/investment.json";
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
	/*
	 * Structure for user.json
	 */
	
	/*
	 * Structure for investment.json
	 */
	Bank() {
		userCount = 0; //adjust this based on user_init
		investmentCount = 0; //adjust this based on investments_init 
		users = new HashMap<String, User>();
		investments = new HashMap<String, Investment>();
<<<<<<< HEAD
		investments_init();
=======
		//investments_init()
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		user_init();
	}

	public static boolean contains(String[] arr, String targetValue) {
	    for (String s: arr) {
	        if (s.equals(targetValue))
	            return true;
	    }
	    return false;
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
<<<<<<< HEAD
		
		String name = (String) bond.get("name");
		System.out.println(name);
		
		double interest = (double) bond.get("interest");
		System.out.println("interest is " + interest);
		
		double price = (double) bond.get("price");
		System.out.println("price is " + price);
		
		Bond temp = new Bond(investmentId, interest, price, name);
		
		investments.put(investmentId, temp);
		
=======
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
	}
	
	private void parseStockObject(JSONObject stock) {
		String investmentId = (String) stock.get("investmentId");
		System.out.println(investmentId);
		
<<<<<<< HEAD
=======
		String type = (String) stock.get("type");
		System.out.println(type);
		
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		double price = (double) stock.get("price");
		System.out.println(price);
			
		String name = (String) stock.get("name");
		System.out.println(name);
		
<<<<<<< HEAD
		double amount = (double) stock.get("amount");
		System.out.println("stock has " + amount);
		
		Stock temp = new Stock(investmentId, price, name, amount);
		
		investments.put(investmentId, temp);
=======
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		
		
		
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
	
	private void parseUserObject(JSONObject user, JSONParser parser)
    {
		String aid;
        //Get user id
        String uid = (String) user.get("uid");
        System.out.println("uid is: " + uid);
        
        //Get user password
        String password = (String) user.get("password");
        System.out.println("password is: " + password);
         
        //Get user name
        String name = (String) user.get("name");
        System.out.println("name is: " + name);
        
        JSONArray js_array = (JSONArray) user.get("aids");
        String[] aid_array = new String[js_array.size()];
        for (int i = 0; i < aid_array.length; i++) {
        	aid_array[i] = (String.valueOf(js_array.get(i)));
        }
        System.out.println(Arrays.toString(aid_array));
        
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
	
<<<<<<< HEAD

 
=======
//	private void parseUserAccount(JSONObject account, User user) {
//		Account temp;
//		
//		//Get account id
//        String aid = (String) account.get("aid");
//        System.out.println("aid is: " + aid);
//        
//        //Get user password
//        String type = (String) account.get("type");
//        System.out.println("type is: " + type);
//        
//        double interest = (String) account.get("interest");
//        System.out.println("interest rate is: " + interest);
//        
//        //Get user name
//        double money = (String) account.get("money");
//        System.out.println("money is: " + money);
//        
//        
//        
//        if (type.equals("checking")) {
//        	temp = new CheckingAccount(aid, AccountType.Checking, money);
//        }
//        else if (type.equals("saving")) {
//        	temp = new SavingAccount(aid, AccountType.Saving, money);
//        }
//        else {
//        	temp = new SecurityAccount(aid, AccountType.Security, money);
//        }
//        
//        try (FileReader reader = new FileReader(transPath)) {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
// 
//            JSONArray transList = (JSONArray) obj;
//            //System.out.println(userList);
//             
//            //Iterate over employee array
//            //userList.forEach( usr -> parseUserObject( (JSONObject) usr ) );
//            for (int i = 0; i < transList.size(); i++) {
//            	JSONObject transObj = (JSONObject)transList.get(i);
//            	if (transObj.containsKey(aid)) {
//            		parseUserTrans(transObj, temp);
//            		user.accounts.put(aid, temp);
//            		break;
//            	}
//            }
//            
//            
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        
//        
//    }
    
    private void parseUserTrans(JSONArray lst) {
    	
    }
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
    
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
	
<<<<<<< HEAD
	public boolean addStock(double p, String n, int amount) {
=======
	public boolean addStock(double p, String n) {
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		if(p <= 0.0) {
			return false;
		}
		String sid = generateInvestmentId();
<<<<<<< HEAD
		Stock temp = new Stock(sid, p, n, amount);
=======
		Stock temp = new Stock(sid, p, n);
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		investments.put(sid, temp);
		return true;
	}
	
<<<<<<< HEAD
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
		Bond temp = new Bond(sid, interest, price, name);
=======
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
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
		investments.put(sid, temp);
		return true;
	}

	public String createAccounts(String name, String password) {
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
		String Aid = u.createCheckingAccount();
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
		String Aid = u.createCheckingAccount(money);
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
<<<<<<< HEAD
			//System.out.println(e.getValue()); 
=======
>>>>>>> b8413d70c9e32007c595483fea5f35bf02d28d2b
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
