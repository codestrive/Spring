package com.codestrive.factoryImpl;

public class SavingsAccount implements Account {
	
	private String accountNumber;
	
	public SavingsAccount(String s){
		System.out.println("Saving Const Called");
		this.accountNumber =s;
	}

	
	public String getDetails(){
		System.out.println("Saving getDetails Called");
		return accountNumber;
	}
}
