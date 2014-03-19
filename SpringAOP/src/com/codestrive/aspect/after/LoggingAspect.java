package com.codestrive.aspect.after;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
	
	/* @After
	 * @AfterReturning
	 * @AfterThrowing
	 * 
	*/
	
	
	@After("execution(public String getInterestRate())")
	public void loggingAdvice(){
		System.out.println("After Advice Run. Get Method called ");
	}
	
	@AfterReturning("execution(public String getInterestRate())")
	public void loggingReturnAdvice(){
		System.out.println("AfterReturning Advice Run. Get Method called ");
	}
	
	/*@AfterThrowing("execution(public String getInterestRate())")
	public void loggingClassAdvice(){
		System.out.println("AfterThrowing Advice Run. Get Method called ");
	}*/
	
	
}
