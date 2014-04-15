package com.codestrive.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.codestrive.jdbcDAOimpl.JdbcDAOImpl;
import com.codestrive.model.State;

public class JdbcDemo {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcDAOImpl dao = ctx.getBean("jdbcDAOImpl", JdbcDAOImpl.class);
	//	State state = dao.getState(21);
		//System.out.println(state.getStateName());
		
		System.out.println("count of state is :" + dao.getStateCount());
		System.out.println("count of state is :" + dao.getStateName(2));
		System.out.println("State name :" + dao.getStateforId(3).getStateName());
		
		//dao.insertState(new State(38, "Your state"));
		System.out.println("Get All circle :" + dao.getAllState().size());
		for (State temp : dao.getAllState()) {
			System.out.println("State --> " +temp.getStateId() +" : "+temp.getStateName());
		}
		
		
		
	}
}
