package com.codestrive.impl;


import java.util.Scanner;
import com.codestrive.appLogic.AccountFactory;
import com.codestrive.appLogic.SavingAccount;

public class Client {

	/*
	 * Main method to initialize factory class and get the details of
	 * Interest rate as per user bank account type
	 * Account Type I used in this demo are -- Saving(S), Fixed(F), PPF(P)
	 * I am taking the user input from console 
	 */
	public static void main(String[] args) {
		AccountFactory acoountFactory = new AccountFactory();
		System.out.println(acoountFactory.getRate(getUserInput()));
		
	}
	
	private static String getUserInput(){
		String accountTypeOption = "";
		Scanner userInput =  new Scanner(System.in);
		System.out.println("Give Account Type(S/F/P) to get Interest rate: ");
		if(userInput.hasNextLine()){
			accountTypeOption = userInput.nextLine();
		}
		userInput.close();
		return accountTypeOption;
	}

}
