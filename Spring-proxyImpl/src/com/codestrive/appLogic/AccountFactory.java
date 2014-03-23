package com.codestrive.appLogic;

import com.codestrive.proxy.SavingAccountProxy;

public class AccountFactory {
	
	public String getRate(String accountType){
		
		System.out.println("Account Factory Called");
		Account userAccount = null;
		
		if("S".equals(accountType))
			userAccount = new SavingAccountProxy("4%");
		if("F".equals(accountType))
			userAccount = new FixedAccount("12%");
		if("P".equals(accountType))
			userAccount = new PPFAccount("7%");
		
		String interestrate  = userAccount.getInterestRate();
		userAccount.setAccountNumber("001");
		//userAccount.getAccountNumber();
	//	userAccount.setBalanceAmount(999);
	//	userAccount.getBalanceAmount();
		//userAccount.setAccountHolderName("Koushik", "Ghosh");
		//userAccount.getAccountHolderName();
		//sa.setAccountNumber("MyAccount01");
		return interestrate;
	}
}
