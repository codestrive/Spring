package com.codestrive.appLogic;

public class AccountFactory {

	public Account manufactureAccount(String accountType){
		System.out.println("Account Factory Called");
		Account userAccount = null;
		if("S".equals(accountType))
			userAccount = new SavingsAccount("X1234");
		if("L".equals(accountType))
			userAccount = new LoanAccount("X4566");
		if("C".equals(accountType))
			userAccount = new CurrentAccount("X789");
		if("T".equals(accountType))
			userAccount = new StudentAccount("S1233");
		
		return userAccount;
	}
}
