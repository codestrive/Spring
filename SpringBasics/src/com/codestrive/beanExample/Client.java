package com.codestrive.beanExample;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//BeanFactory factory = new XmlBeanFactory(new FileSystemResource("springBean.xml"));
		ApplicationContext context = new ClassPathXmlApplicationContext("springContext.xml");
		PaymentService payment = (PaymentService) context.getBean("paymentService");
		payment.pay();

	}
	

}
