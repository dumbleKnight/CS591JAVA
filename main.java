
public class main {
	/*
	 * Things to change:
	 * 1. the way aid is incremented, probably should have a accountCount in bank as a global var to keep track of the next aid
	 */
	static public void main(String[] args) { 
		Bank bank = new Bank();
		//bank.getUserCount();
		//bank.showAccounts();
		//bank.writeUser();
		//bank.writeAccount();
		bank.writeTransaction();
		//System.out.println(bank.getInvestmentInfo());
	}
}
