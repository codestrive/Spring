package com.codestrive.contextExample;

import com.codestrive.appLogic.Account;
import com.codestrive.appLogic.AccountFactory;

public class PaymentService {
	
	//private SavingsAccount savingsAccount;
	private Account userAccount;
	private String accountType;
	
	public String getAccountType() {
		return accountType;
	}

	//public void setAccountType(String accountType) {
		//this.accountType = accountType;
	//}
	 
	public PaymentService(String accountType){
		this.accountType = accountType;
		System.out.println("PaymentService called() "+getAccountType());
		userAccount = new AccountFactory().manufactureAccount(getAccountType());
	}
	
	public void accountNumber(){
		System.out.println("Payment using tight coupling --> " + this.userAccount.getDetails());
	}

}
