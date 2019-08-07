
public class CheckingAccount extends Account {

	CheckingAccount(String id) {
		super(id, AccountType.Checking);
		interestRate = 0.01;
	}
	
	CheckingAccount(String id, double m) {
		super(id, AccountType.Checking, m);
		interestRate = 0.01;
		
	}
}
