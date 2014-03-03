package com.codestrive.tightCouple;

import java.util.Scanner;

public class Client {
	
	public static void main(String args[]){
		PaymentService payment = new PaymentService();
		payment.pay();
	}
	private static String getUserInput(){
		
		String accountTypeOption = "";
		Scanner userInput =  new Scanner(System.in);
		System.out.println("Which account(S/C/L) do you want to use");
		if(userInput.hasNextLine()){
			accountTypeOption = userInput.nextLine();
		}
		userInput.close();
		return accountTypeOption;
	}
	
}
