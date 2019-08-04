
public class Transaction {
       public TransactionType type;
       public String sender ; // uid
       public String receiver ; // uid
       public double money;
       public String investmentId;
       public double amount;
       public String date;
       
       // Withdraw, loan, save
       Transaction(TransactionType t, String s, double m, String d) {
    	   type = t;
    	   sender = s;
    	   money = m;
    	   date = d;
       }
       
       // Send, Receive 
       Transaction(TransactionType t, String s, String r, double m, String d){
    	   type = t;
    	   sender = s;
    	   receiver = r;
    	   money = m;
    	   date = d;
       }
       
       // Buy, Sell
       Transaction(TransactionType t, String s, String i, double m, double am, String d){
    	   type =t;
    	   sender = s;
    	   investmentId = i;
    	   money = m;
    	   amount = am;
    	   date = d;
       }
       
       
       public String toString( ) {
    	   StringBuilder sb = new StringBuilder();
    	   switch(type) {
    	   	 case SEND:
    	   		 sb.append("Account " + sender +" sends "+ money + " to User " + receiver + " on " + date);
    	   		 break;
    	   	
    	   	 case RECEIVE:
    	   		 sb.append("Account " + receiver + " receives " + money + " from User " +  receiver + " on " + date);
    	   		 break;
    	     
    	   	 case BUY:
    	   		 sb.append("Account " + sender + " buys " + amount + " amount of " + investmentId + " with money " + money + " on " + date);
    	   		 break;
    	   	 case WITHDRAW :
    	   		 sb.append("Account " + sender + " withdraws " + money+ " on " + date);
    	   		 break;
    	   	
    	   	 case LOAN :
    	   		sb.append("Account " + sender + " borrows " + money+ " on " + date);
   	   		 	break; 
    	   	 case SAVE :
    	   		 sb.append("Account " + sender + " saves " + money + " on " + date);
    	   		 break;
   	   		 // SELL
    	     default:
    	    	 sb.append("User " + sender + " sells " + amount + " amount of " + investmentId + " and obtains " + money + " on " + date);
    	   		 
    	   }
    	   return sb.toString();
       }
       
       public static void main(String[] args) {
    	   //Transaction t = new Transaction(TransactionType.SEND, "01", "02", 100.0);
    	   //System.out.println(t);
       }
}
