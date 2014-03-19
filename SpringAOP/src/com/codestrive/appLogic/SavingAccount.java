package com.codestrive.appLogic;

public class SavingAccount implements Account {
	/*
	 * Saving account
	 */
	private String rateOfInterest;
	private String accountNumber;
	private int balanceAmount;
	private String firstName;
	private String lastName;
	
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
		this.accountNumber = accountNumber;
		System.out.println("setAccountNumber of "+ this.getClass().getName() + " :"+this.accountNumber );
		//throw new RuntimeException();
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
		this.balanceAmount = balanceAmount;
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
		//throw(new RuntimeException());
		return this.firstName +" " + this.lastName;
	}

	

}
