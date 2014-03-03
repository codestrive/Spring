package com.codestrive.interfaceImpl;

public class LoanAccount implements Account{

	private String accountNumber;
	
	public LoanAccount(String s){
		System.out.println("Loan Const Called");
		this.accountNumber =s;
	}

	
	public String getDetails(){
		System.out.println("Loan getDetails Called");
		return accountNumber;
	}
}
