package com.codestrive.factoryImpl;

public class PaymentService {
	
	//private SavingsAccount savingsAccount;
	private Account userAccount;
	
	public PaymentService(String accountType){
		System.out.println("PaymentService called() ");
		userAccount = new AccountFactory().manufactureAccount(accountType);
	}
	
	public void pay(){
		System.out.println("Payment using tight coupling --> " + this.userAccount.getDetails());
	}

}
