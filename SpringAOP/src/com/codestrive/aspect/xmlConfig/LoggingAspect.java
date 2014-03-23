package com.codestrive.aspect.xmlConfig;

public class LoggingAspect {
	
	public void firstAdvice(){
		System.out.println("First : Before Advice get called");
	}
	
	
	public void secondAdvice(){
		System.out.println("Second : Before Advice get called");
	}
	
	
	public void argumensts(){
		System.out.println("arguments with string parameter");
	}
	
	
	public void arguments(String account){
		System.out.println("arguments with string parameter and para value : "+account);
	}
	
	
}
