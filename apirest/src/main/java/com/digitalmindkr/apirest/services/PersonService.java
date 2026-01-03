package com.digitalmindkr.apirest.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.controllers.TestLogController;
import com.digitalmindkr.apirest.exception.ResourceNotFoundException;
import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.repository.PersonRepository;

@Service
public class PersonService {

	//private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	PersonRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName()); //faço a adição de um logger para a classe
	
	public Person create(Person person) {
		logger.info("Creating one person");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Updating one person");
		Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person");
		repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.deleteById(id);
	}
	
	public Person findById(Long id) {
		logger.info("Finding one person");
		return repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
	}
	
	public List<Person> findAll(){
		logger.info("Finding all peoples");
		return repository.findAll();
	}

	
}
