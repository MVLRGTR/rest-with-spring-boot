package com.digitalmindkr.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalmindkr.apirest.data.dto.PersonDTO;
import com.digitalmindkr.apirest.services.PersonService;


@RestController
@RequestMapping(value = "/person")
public class PersonController {

	@Autowired // faz a injeçao de dependencia de forma transparente ao programador 
	private PersonService service;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO create(@RequestBody PersonDTO person) {
		return service.create(person);
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO update(@RequestBody PersonDTO person) {
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}" )
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonDTO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/{id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	
}
