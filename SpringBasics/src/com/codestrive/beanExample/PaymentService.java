package com.codestrive.beanExample;

import com.codestrive.appLogic.Account;
import com.codestrive.appLogic.AccountFactory;

public class PaymentService {
	
	//private SavingsAccount savingsAccount;
	private Account userAccount;
	private String accountType;
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public PaymentService(){
		System.out.println("PaymentService called() "+getAccountType());
		
	}
	
	public void pay(){
		userAccount = new AccountFactory().manufactureAccount(getAccountType());
		System.out.println("Payment using tight coupling --> " + this.userAccount.getDetails());
	}

}
