package com.codestrive.appLogic;

public interface Account {

	/*
	 * Interface to declare the minimum feature to adopt by subclass
	 */
	
	
	public String getInterestRate();
	
	public int getBalanceAmount();
	
	public void setBalanceAmount(int balanceAmount);
	
	public String getAccountNumber();
	
	public void setAccountNumber(String accountNumber);
	
	public void setAccountHolderName(String firstName, String lastName);
	
	public String getAccountHolderName();
	
}
