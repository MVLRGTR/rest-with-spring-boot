package com.digitalmindkr.apirest.controllers;

//import java.util.Date;
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

import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;
import com.digitalmindkr.apirest.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/person")
@Tag(name = "People" , description = "Endpoints for managing People") //swagger
public class PersonController {

	@Autowired // faz a injeçao de dependencia de forma transparente ao programador 
	private PersonService service;
	
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
	
	@DeleteMapping(value = "/{id}" )
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_YAML_VALUE})
	@Operation(summary = "Find All People",
	description = "Finds All People",
	tags = {"People"},
	responses = {
			@ApiResponse(
					description = "Success" , 
					responseCode  = "200" , 
					content = {
							@Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
									)
					}),
			@ApiResponse(description = "No Content" , responseCode  = "204" , content = @Content),
			@ApiResponse(description = "Bad Request" , responseCode  = "400" , content = @Content),
			@ApiResponse(description = "Unauthorized" , responseCode  = "401" , content = @Content),
			@ApiResponse(description = "Not Found" , responseCode  = "404" , content = @Content),
			@ApiResponse(description = "Internal Serve Error" , responseCode  = "500" , content = @Content)
	}
	)
	public List<PersonDTO> findAll() {
		return service.findAll();
	}
	
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
