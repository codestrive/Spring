package com.ikiosksng.airkiosk.hub;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ikiosksng.airkiosk.hub.terminal.internal.TerminalRepository;


@ContextConfiguration("classpath:META-INF/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpringConfigTest {
	
	@PersistenceUnit
	EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	TerminalRepository terminalRepository;
	//@Test
	public void testlog4j(){
		 ClassLoader loader = SpringConfigTest.class.getClassLoader();
	        System.out.println(loader.getResource("SpringConfigTest.class"));

	}

	@Test
	public void testEntityManagerConfig() {
		assertNotNull(entityManagerFactory);
		assertNotNull(entityManager);
		entityManager.createQuery("from Terminal").getResultList();
		assertNotNull(terminalRepository);
		assertNotNull(terminalRepository.findAll());
		File file = new File("selva.txt");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
