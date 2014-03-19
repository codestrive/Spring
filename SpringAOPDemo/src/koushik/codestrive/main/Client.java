package koushik.codestrive.main;

import koushik.codestrive.service.ShapeService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("springBean.xml");
		ShapeService shapeService = context.getBean("shapeService", ShapeService.class);
		//System.out.println("Getting the Circle Name:"+shapeService.getCircle().getName());
		shapeService.getCircle().setNameReturn("dummy");
	}

}
