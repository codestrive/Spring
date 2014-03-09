package com.codestrive.appLogic;

public class SavingAccount implements Account {
	/*
	 * Saving account
	 */
	private String rateOfInterest;
	
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

}
