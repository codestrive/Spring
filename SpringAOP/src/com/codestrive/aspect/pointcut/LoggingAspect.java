package com.codestrive.aspect.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {
	
	/* @Before("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	 * @Before("execution(public String *.getInterestRate())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(public String *.get*(..))")
	 * @Before("execution(public * *.get*())")
	 * @Before("execution(* * *.get*())")
	 * @Before("args(someString)") // string integer or any type
	*/
	
	@Before("loggingAdvicePointcut()")
	public void loggingAdvice(){
		System.out.println("PointCut Advice Run. Get Method called ");
	}
	
	@Pointcut("execution(public String getInterestRate())")
	public void loggingAdvicePointcut(){
		
	}
	
}
