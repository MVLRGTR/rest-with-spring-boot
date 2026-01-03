package com.digitalmindkr.apirest.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.controllers.TestLogController;
import com.digitalmindkr.apirest.exception.ResourceNotFoundException;
import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.data.dto.PersonDTO;
import com.digitalmindkr.apirest.repository.PersonRepository;

import static com.digitalmindkr.apirest.mapper.ObjectMapper.parseListObjects;
import static com.digitalmindkr.apirest.mapper.ObjectMapper.parseObject;

@Service
public class PersonService {

	//private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	PersonRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName()); //faço a adição de um logger para a classe
	
	public PersonDTO create(PersonDTO person) {
		logger.info("Creating one person");
		var entity = parseObject(person, Person.class);
		return parseObject(repository.save(entity), PersonDTO.class);
	}
	
	public PersonDTO update(PersonDTO person) {
		logger.info("Updating one person");
		Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return parseObject(entity, PersonDTO.class);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person");
		repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.deleteById(id);
	}
	
	public PersonDTO findById(Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return parseObject(entity, PersonDTO.class);
		
	}
	
	public List<PersonDTO> findAll(){
		logger.info("Finding all peoples");
		return parseListObjects(repository.findAll(), PersonDTO.class);
	}

	
}
