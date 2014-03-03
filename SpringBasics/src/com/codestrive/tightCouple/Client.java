package com.codestrive.tightCouple;


public class Client {
	
	public static void main(String args[]){
		PaymentService payment = new PaymentService();
		payment.pay();
	}
	
	
}
