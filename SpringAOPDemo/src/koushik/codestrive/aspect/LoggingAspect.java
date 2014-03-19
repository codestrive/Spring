package koushik.codestrive.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
	
	/* @Before("execution(public String *.getName())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(public String *.get*())")
	 * @Before("execution(* * *.get*())")
	 * @Pointcut
	 * @After
	 * @AfterReturning
	 * @AfterThrowing
	 * @AfterReturning(pointcut="args(name)",returning="returnString")
	 * @Around()
	 *  ProceedingJointPoint proceedingJointPoint
	*/
	
	
	@AfterReturning(pointcut="args(name21)",returning="returnString")
	public void loggingAdvice(String name21, String returnString){
		System.out.println("Advice Run. "+name21+" Get Method called "+returnString);
	}
	
	@AfterThrowing("args(name21)")
	public void exceptionAdvice(String name21){
		System.out.println("an exception has been thrown");
	}
	
}
