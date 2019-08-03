
public class CheckingAccount extends Account {

	CheckingAccount(String id, AccountType t) {
		super(id, t);
		interestRate = 0.01;
	}
	
	CheckingAccount(String id, AccountType t, double m) {
		super(id, t, m);
		interestRate = 0.01;
		
	}
}
