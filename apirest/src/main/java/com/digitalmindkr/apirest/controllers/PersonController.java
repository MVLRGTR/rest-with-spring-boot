package com.digitalmindkr.apirest.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable("id") String id) {
		return service.findById(id);
	}
}
