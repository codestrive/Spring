package com.codestrive.tightCouple;

public class PaymentService {
	
	private SavingsAccount savingsAccount;
	
	public PaymentService(){
		System.out.println("PaymentService called() ");
		savingsAccount = new SavingsAccount("X1234");
	}
	
	public void pay(){
		System.out.println("Payment using tight coupling --> " + this.savingsAccount.getDetails());
	}

}
