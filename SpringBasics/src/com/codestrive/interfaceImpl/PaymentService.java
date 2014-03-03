package com.codestrive.interfaceImpl;

public class PaymentService {
	
	//private SavingsAccount savingsAccount;
	private Account userAccount;
	
	public PaymentService(String accountType){
		System.out.println("PaymentService called() ");
		if("S".equals(accountType))
			userAccount = new SavingsAccount("X1234");
		if("L".equals(accountType))
			userAccount = new LoanAccount("X4566");
		if("C".equals(accountType))
			userAccount = new CurrentAccount("X789");
	}
	
	public void pay(){
		System.out.println("Payment using tight coupling --> " + this.userAccount.getDetails());
	}

}
