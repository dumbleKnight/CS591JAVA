
public class SavingAccount extends Account{

	SavingAccount(String id, AccountType t) {
		super(id, t);
		interestRate = 0.03;
	}
	
	SavingAccount(String id, AccountType t, double m) {
		super(id, t, m);
		interestRate = 0.03;
	}
	
	
}
