package com.digitalmindkr.apirest.controllers;

//import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalmindkr.apirest.controllers.docs.PersonControllerDocs;
import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;
import com.digitalmindkr.apirest.services.PersonService;

import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin(origins = "http://localhost:8080") caso queira implementar CORS em cada controller 
@RestController
@RequestMapping(value = "/person")
@Tag(name = "People" , description = "Endpoints for managing People") //swagger
public class PersonController implements PersonControllerDocs {

	@Autowired // faz a injeçao de dependencia de forma transparente ao programador 
	private PersonService service;
	
	@Override
	@CrossOrigin(origins = {"http://localhost:8080","https://www.digitalmindkr.com"})
	@PostMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE} ,
				consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE})
	public PersonDTO create(@RequestBody PersonDTO person) {
		return service.create(person);
	}
	
	@Override
	@PutMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE} ,
				consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE})
	public PersonDTO update(@RequestBody PersonDTO person) {
		return service.update(person);
	}
	
	@Override
	@DeleteMapping(value = "/{id}" )
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE})
	public List<PersonDTO> findAll() {
		return service.findAll();
	}
	
	@Override
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}" ,
	    produces = {
	    	MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE})
	public PersonDTO findById(@PathVariable("id") Long id) {
		var person = service.findById(id);
		//person.setBirthDay(new Date());
		//person.setPhoneNumber("+55 (34) 98765-8956");
		//person.setSensitiveData("Foo Bar");
		return person;
	}
	
}
