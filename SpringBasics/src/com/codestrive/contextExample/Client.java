package com.codestrive.contextExample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class Client {
	
	public static void main(String args[]){
		ApplicationContext context = new ClassPathXmlApplicationContext("springContextContructorBased.xml");
		PaymentService payment = (PaymentService) context.getBean("paymentService");
		
		payment.accountNumber();
	}
}
