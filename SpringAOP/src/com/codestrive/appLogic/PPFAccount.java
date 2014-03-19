package com.codestrive.appLogic;

public class PPFAccount implements Account {

	/*
	 * PPF account
	 */
	private String rateOfInterest;
	private String accountNumber;
	private int balanceAmount;
	private String firstName;
	private String lastName;
	
	
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

	public void setAccountNumber(String accountNumber){
		System.out.println("setAccountNumber of "+ this.getClass().getName() + " :"+this.accountNumber );
		throw new RuntimeException();
	}

	@Override
	public String getAccountNumber() {
		System.out.println("getAccountNumber of "+ this.getClass().getName() + " :"+this.accountNumber);
		return null;
	}

	public int getBalanceAmount() {
		System.out.println("getBalanceAmount of "+ this.getClass().getName() + " :"+this.balanceAmount);
		return balanceAmount;
	}

	public void setBalanceAmount(int balanceAmount) {
		System.out.println("setBalanceAmount of "+ this.getClass().getName() + " :"+this.balanceAmount );
		this.balanceAmount = balanceAmount;
	}
	
	@Override
	public void setAccountHolderName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		System.out.println("setAccountHolderName of "+ this.getClass().getName() + " :"+this.firstName +" "+ this.lastName );
		
	}

	@Override
	public String getAccountHolderName() {
		System.out.println("getAccountHolderName of "+ this.getClass().getName() + " :"+this.firstName +" "+ this.lastName );
		return this.firstName +" " + this.lastName;
	}
}
