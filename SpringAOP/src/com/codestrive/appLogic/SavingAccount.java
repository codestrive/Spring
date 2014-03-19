package com.codestrive.appLogic;

public class SavingAccount implements Account {
	/*
	 * Saving account
	 */
	private String rateOfInterest;
	private String accountNumber;
	
	public SavingAccount(String s){
		System.out.println("SavingAccount Constructor Called");
		this.rateOfInterest =s;
	}

	@Override
	public String getInterestRate() {
		System.out.println("In saving account class: getInterestRate");
		String message = "Saving Account: "+ rateOfInterest +" interset per annum";
		return message;
	}
	
	public void setAccountNumber(String accountNumber){
		System.out.println("Saving Setter called with account number:"+accountNumber );
		throw new RuntimeException();
	}

}
