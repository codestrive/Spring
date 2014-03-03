package com.codestrive.factoryImpl;

public class StudentAccount implements Account {
	
	private String accountNumber;
	
	private String institute;
	
	public StudentAccount(String s){
		System.out.println("Student Const Called");
		this.accountNumber =s;
	}

	
	public String getDetails(){
		System.out.println("Student getDetails Called");
		return accountNumber;
	}


	public String getInstitute() {
		return institute;
	}


	public void setInstitute(String institute) {
		this.institute = institute;
	}
	
	
}
