package com.codestrive.proxy;

import com.codestrive.appLogic.SavingAccount;
import com.codestrive.aspect.before.LoggingAspect;

public class SavingAccountProxy extends SavingAccount {
	
	
	public SavingAccountProxy(String s) {
		super(s);
	}

	public String getInterestRate() {
		new LoggingAspect().firstAdvice();
		return super.getInterestRate();
	}

}
