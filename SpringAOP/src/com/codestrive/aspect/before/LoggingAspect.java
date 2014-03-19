package com.codestrive.aspect.before;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
	
	/* 
	 * @Before("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	 * @Before("execution(public String *.getInterestRate())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(public String *.get*(..))")
	 * @Before("execution(public String *.get*(*))")
	 * @Before("execution(public * *.get*())")
	 * @Before("execution(* * *.get*())")
	 * @Before("args(someString)") // string integer or any type
	 * 
	*/
	
	@Before("execution(public * get*())")
	public void firstAdvice(){
		System.out.println("First : Before Advice get called");
	}
	
	@Before("execution(public * set*(..))")
	public void secondAdvice(){
		System.out.println("Second : Before Advice get called");
	}
	
	@Pointcut("execution(public * get*())")
	public void allGetters(){}
}
