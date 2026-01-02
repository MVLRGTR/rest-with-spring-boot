package com.digitalmindkr.apirest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.model.Person;

@Service
public class PersonService {

	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonService.class.getName()); //faço a adição de um logger para a classe
	
	public Person create(Person person) {
		logger.info("Creating one person");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Updating one person");
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deleting one person");
	}
	
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
	
	public List<Person> findAll(){
		logger.info("Finding all peoples");
		List<Person> persons = new ArrayList<Person>();
		for(int i = 0 ; i < 8 ; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons;
	}

	private Person mockPerson(int i) {
		Person p = new Person();
		p.setId(counter.incrementAndGet());
		p.setFirstName("First name :" + i);
		p.setLastName("Last name : "+ i);
		p.setAddress("Mont serrat");
		p.setGender("Male");
		return p;
	}
}
