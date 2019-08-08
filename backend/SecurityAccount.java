package backend;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;

public class SecurityAccount extends Account {
	private static String propertyPath = "src/backend/property.json";
	private HashMap<String, Property> property; //<key : sid, value : property>
	
	SecurityAccount(String id, double m) {
		super(id, AccountType.Security, m);
		property = new HashMap<String, Property>();
	}
	
	public String getNow(String sid) {
		return parseInstant(property.get(sid).getNow());
	}
	
	public String getInvestment() {
		StringBuilder sp = new StringBuilder();
		
		for(Entry<String, Property> investment: property.entrySet()) {
			sp.append(investment);
			sp.append("|");
		}
		return sp.toString();
	}
	
	public void parseProperty() {
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(propertyPath))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray propertyList = (JSONArray) obj;
            for (int i = 0; i < propertyList.size(); i++) {
            	JSONObject properties = (JSONObject) propertyList.get(i);
            	if (properties.containsKey(Aid)) {
            		insertProperties( (JSONArray) properties.get(Aid));
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
	
	private void insertProperties(JSONArray properties) {
		String type;
		String investmentId;
		double price;
		String name;
		double amount;
		double interest;
		Instant due;
		Instant now;
		
		for (int i = 0 ;i < properties.size(); i++) {
			JSONObject propertyObj = (JSONObject) properties.get(i);
			type = (String) propertyObj.get("type");
			if (type.equals("Stock")) {
				investmentId = (String) propertyObj.get("investmentId");
				name = (String) propertyObj.get("name");
				price = (double) propertyObj.get("price");
				amount = (double) propertyObj.get("amount");
				now = parseTime((String) propertyObj.get("now"));
				Stock invest = new Stock(investmentId, price, name);
				System.out.println(invest);
				Property prop = new Property(amount, invest, now); // deal with instant later 
				property.put(investmentId, prop);
			}
			else {
				investmentId = (String) propertyObj.get("investmentId");
				name = (String) propertyObj.get("name");
				amount = (double) propertyObj.get("amount");
				interest = (double) propertyObj.get("interest");
				due = parseTime((String) propertyObj.get("due"));
				now = parseTime((String) propertyObj.get("now"));
				Bond invest = new Bond(investmentId, interest, name);
				System.out.println(invest);
				Property prop = new Property(invest, now, due, amount, interest);
				property.put(investmentId, prop);
				
			}
		}
	}
	
	public void storeProperty(JSONArray finalJSON) {
		JSONObject aidObj = new JSONObject();
		JSONArray propList = new JSONArray();
		for (Property prop : property.values()) {
			JSONObject propObj = new JSONObject();
			String type = prop.getType();
			propObj.put("type", type);
			if (type.equals("Bond")) {
				String investmentId = prop.getID();
				String name = prop.getName(); 
				String now = parseInstant(prop.getNow());
			    String due = parseInstant(prop.getDue());
			    double amount = prop.getAmount();
			    double interest = prop.getInterest();
			    
			    propObj.put("investmentId", investmentId);
			    propObj.put("name", name);
			    propObj.put("now", now);
			    propObj.put("due", due);
			    propObj.put("amount", amount);
			    propObj.put("interest", interest);
			}
			else {
				String investmentId = prop.getID();
				String name = prop.getName(); 
				String now = parseInstant(prop.getNow());
				double price = prop.getPrice();
				double amount = prop.getAmount();
				
				propObj.put("investmentId", investmentId);
			    propObj.put("name", name);
			    propObj.put("now", now);
			    propObj.put("price", price);
			    propObj.put("amount", amount);
			    
			}
			propList.add(propObj);
		}
		aidObj.put(Aid,propList);
		finalJSON.add(aidObj);
	}
	
	private Instant parseTime(String time) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "M/d/uu h:mm a" , Locale.US );  // Specify locale to determine human language and cultural norms used in translating that input string.
		LocalDateTime ldt = LocalDateTime.parse( time , f );
		ZoneId z = ZoneId.of( "America/Toronto" ) ;
		java.time.ZonedDateTime zdt = ldt.atZone( z ) ; 
		Instant instant = zdt.toInstant() ;
		return instant;
	}
	
	private String parseInstant(Instant time) {
		DateTimeFormatter formatter =
			    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
			                     .withLocale( Locale.US )
			                     .withZone( ZoneId.systemDefault() );
		String output = formatter.format( time );
		return output;
	}
	
	public boolean buyBond(double Money, Investment i, Property.InterestRate irate, double interest) {
		if(money < Money || Money <= 0.0 || property.containsKey(i.getId())) {
			return false;
		}
		money = money - Money;
		property.put(i.getId(), new Property(Money, i, irate, interest));
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.BUY, Aid, i.getId(), Money, Money, parseInstant(now)));
		return true;		
	}
	
	public boolean buyStock(Investment i, int amount, double price) {
		double expense = (double)amount * price;
		if(amount <= 0 || expense <= 0.0 || money < expense) {
			return false;
		} 
		money = money - expense;
		
		Property temp;
		if(property.containsKey(i.getId())) {
		   temp = property.get(i.getId());
		   temp.add((double) amount);
		}else {
		   temp = new Property((double) amount, i);
		}
		property.put(i.getId(), temp);
		
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.BUY, Aid, i.getId(), expense, (double)amount, parseInstant(now)));
		return true;
	}
	
	public boolean sellBond(String sid) {
		if(!property.containsKey(sid)) {
			return false;
		}
		
		double amount = property.get(sid).getAmount();
		double profit = property.get(sid).sell();
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.SELL, Aid, sid, profit, amount, parseInstant(now)));
		money = money + profit;
		property.remove(sid);
		
		return true;		
	}
	
	public boolean sellStock(String sid, double amount, double price) {
		if(!property.containsKey(sid) || amount > property.get(sid).getAmount()) {
			return false;
		}
		Property temp = property.get(sid);
		double profit = temp.sell(amount, price);
		property.put(sid, temp);
		
		Instant now = Instant.now();
		transactions.add(new Transaction(TransactionType.SELL, Aid, sid, profit, amount, parseInstant(now)));
		money = money + profit;
		
		if(property.get(sid).empty()) {
			property.remove(sid);
		}
		return true;
	}
	
	
	

}
