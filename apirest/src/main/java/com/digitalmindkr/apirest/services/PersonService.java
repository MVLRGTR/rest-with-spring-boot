package com.digitalmindkr.apirest.services;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.model.Person;

@Service
public class PersonService {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName()); //faço a adição de um logger para a classe
	
	public Person findById(String id) {
		logger.info("Finding one person");
		Person p = new Person();
		p.setId(counter.incrementAndGet());
		p.setFirstName("Marcos");
		p.setLastName("Vincius");
		p.setAddress("Mont serrat");
		p.setGender("Male");
		
		return p;
	}
	
	
}
