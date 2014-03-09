package com.codestrive.appLogic;

public class PPFAccount implements Account {

	/*
	 * PPF account
	 */
	private String rateOfInterest;
	
	public PPFAccount(String s){
		System.out.println("PPFAccount Constructor Called");
		this.rateOfInterest =s;
	}
	
	@Override
	public String getInterestRate() {
		System.out.println("In ppf account class: getInterestRate");
		String message = "PPF Account: "+ rateOfInterest +" interset per annum";
		return message;
	}

}
