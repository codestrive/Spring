package com.codestrive.aspect.around;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
	
	/* @Around("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	 * @Around("execution(public String *.getInterestRate())")
	 * @Around("execution(public String *.get*())")
	 * @Around("execution(public String *.get*(..))")
	 * @Around("execution(public String *.get*(*))")
	 * @Around("execution(public * *.get*())")
	 * @Around("execution(* * *.get*())")
	 * @Around("args(someString)") // string integer or any type
	*/
	
	@Around("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	public void loggingAdvice(){
		System.out.println("Around Advice Run. Get Method called ");
	}
	
}
