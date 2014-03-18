package com.codestrive.aspect.around;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
	
	/* @Before("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	 * @Before("execution(public String *.getInterestRate())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(public String *.get*(..))")
	 * @Before("execution(public String *.get*(*))")
	 * @Before("execution(public * *.get*())")
	 * @Before("execution(* * *.get*())")
	 * @Before("args(someString)") // string integer or any type
	*/
	
	@Before("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	public void loggingAdvice(){
		System.out.println("Before Advice Run. Get Method called ");
	}
	
}
