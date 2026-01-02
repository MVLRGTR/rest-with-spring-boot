package com.digitalmindkr.apirest.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.services.PersonService;


@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired // faz a injeçao de dependencia de forma transparente ao programador 
	private PersonService service;
	
	@RequestMapping(method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}
	
	@RequestMapping(method = RequestMethod.PUT ,produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) {
		return service.update(person);
	}
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		service.delete(id);
	}
	
	@RequestMapping(method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll() {
		return service.findAll();
	}
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	
}
