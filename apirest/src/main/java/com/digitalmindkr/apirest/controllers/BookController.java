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

import com.digitalmindkr.apirest.controllers.docs.BookControllerDocs;
import com.digitalmindkr.apirest.data.dto.v1.BookDTO;
import com.digitalmindkr.apirest.services.BookService;


import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/book")
@Tag(name = "Book" , description = "Endpoints for managing Book")
public class BookController implements BookControllerDocs{
	
	@Autowired
	private BookService service;
	
	@Override
	@PostMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE} ,
				consumes = {
			MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE ,
			MediaType.APPLICATION_YAML_VALUE})
	public BookDTO create(@RequestBody BookDTO book) {
		return service.create(book);
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
	public BookDTO update(@RequestBody BookDTO book) {
		return service.update(book);
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
	public List<BookDTO> findAll() {
		return service.findAll();
	}

	@Override
	@GetMapping(value = "/{id}" ,
    produces = {
    	MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_XML_VALUE,
		MediaType.APPLICATION_YAML_VALUE})
	public BookDTO findById(@PathVariable("id") Long id) {
		var book = service.findById(id);
		return book;
	}

}
