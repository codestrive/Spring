package com.codestrive.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.codestrive.jdbcDAOimpl.JdbcDAOImpl;
import com.codestrive.model.State;

public class JdbcMain {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcDAOImpl dao = ctx.getBean("jdbcDAOImpl", JdbcDAOImpl.class);
		//State state = dao.getState(21);
		//System.out.println(state.getStateName());
		
		System.out.println("count of state is :" + dao.getStateCount());
		System.out.println("count of state is :" + dao.getStateName(2));
		System.out.println("State name :" + dao.getStateforId(3).getStateName());
		
		//dao.insertState(new State(39, "Your state"));
		dao.deleteState(39);
		System.out.println("Get All circle :" + dao.getAllState().size());
		for (State temp : dao.getAllState()) {
			System.out.println("State --> " +temp.getStateId() +" : "+temp.getStateName());
		}
		
		String abc =new String("hello");
		String xyz =new String("hello");
	/*	String a = abc;
		
		System.out.println(abc == a);
		System.out.println(a.equals(abc));*/
		
		System.out.println(xyz == abc);
		System.out.println(xyz.equals(abc));
		
		System.out.println("addition of number :" + addtion(10,2,3,4));
	}
	
	public static int addtion(int... nums){
		
		int c = 0;
		for(int num : nums){
			c +=num;
		}
		return c;
	}
}
