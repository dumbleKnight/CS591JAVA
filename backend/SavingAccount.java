package backend;

public class SavingAccount extends Account{

	SavingAccount(String id) {
		super(id, AccountType.Saving);
		interestRate = 0.03;
	}
	
	SavingAccount(String id, double m) {
		super(id, AccountType.Saving, m);
		interestRate = 0.03;
	}
	
	
}
