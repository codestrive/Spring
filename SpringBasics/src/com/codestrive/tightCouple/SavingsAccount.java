package com.codestrive.tightCouple;

public class SavingsAccount {
	
	private String accountNumber;
	
	public SavingsAccount(String s){
		System.out.println("Saving Const Called");
		this.accountNumber =s;
	}

	public String getDetails(){
		return accountNumber;
	}
}
