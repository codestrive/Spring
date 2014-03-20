package com.codestrive.aspect.around;

import org.aspectj.lang.ProceedingJoinPoint;
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
	public void loggingAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
		
		try {
			System.out.println("Around Advice Run. Before Get Method called ");
			proceedingJoinPoint.proceed();
			System.out.println("Around Advice Run. after returning Get Method called ");
			
		} catch (Throwable e) {
			System.out.println("After throw exception");
		}
		System.out.println("Around Advice Run. After finally Method called ");
		
	}
	
	/*@Around("execution(public String com.codestrive.appLogic.SavingAccount.getInterestRate())")
	public Object loggingAdvice(ProceedingJoinPoint proceedingJoinPoint){
		Object retuenValue = null;
		try {
			System.out.println("Around Advice Run. Before Get Method called ");
			retuenValue = proceedingJoinPoint.proceed();
			System.out.println("Around Advice Run. after returning Get Method called ");
			
		} catch (Throwable e) {
			System.out.println("After throw exception");
		}
		System.out.println("Around Advice Run. After finally Method called ");
		return retuenValue;
	}*/
	
}
