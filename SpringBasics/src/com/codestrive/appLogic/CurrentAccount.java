package com.codestrive.appLogic;

public class CurrentAccount implements Account {
	private String accountNumber;
	
	public CurrentAccount(String s){
		System.out.println("Saving Const Called");
		this.accountNumber =s;
	}
	
	public String getDetails(){
		System.out.println("Currents getDetails Called");
		return accountNumber;
	}
}
