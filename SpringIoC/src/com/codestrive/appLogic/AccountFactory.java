package com.codestrive.appLogic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountFactory {
	
	public String getRate(String accountType){
		/*
		 * Account Factory class: It will initiate the require subclass as
		 * per user input from the Client class
		 * 
		 * In future if some new account type is introduced we just have 
		 * to create a subclass and put the logic to define the class here
		 * Client and Main Business logic need not to change anything.
		 * This is the beauty of Factory Pattern.
		 */
		System.out.println("Account Factory Called");
		Account userAccount = null;
		ApplicationContext factory = new ClassPathXmlApplicationContext("springContextContructorBased.xml");
		//BeanFactory factory = new XmlBeanFactory(new FileSystemResource("springBean.xml"));
		
		if("S".equals(accountType))
			userAccount = factory.getBean("savingAccount",Account.class);
		if("F".equals(accountType))
			userAccount = (Account) factory.getBean("fixedAccount");
		if("P".equals(accountType))
			userAccount = (Account) factory.getBean("ppfAccount");
		
		String interestrate  = userAccount.getInterestRate();
		return interestrate;
	}
}
