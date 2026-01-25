package com.digitalmindkr.apirest.services;

import static com.digitalmindkr.apirest.mapper.ObjectMapper.parseListObjects;
import static com.digitalmindkr.apirest.mapper.ObjectMapper.parseObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import com.digitalmindkr.apirest.controllers.BookController;
import com.digitalmindkr.apirest.controllers.TestLogController;
import com.digitalmindkr.apirest.data.dto.v1.BookDTO;
import com.digitalmindkr.apirest.exception.RequiredObjectIsNullException;
import com.digitalmindkr.apirest.exception.ResourceNotFoundException;
import com.digitalmindkr.apirest.model.Book;
import com.digitalmindkr.apirest.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());
	
	public BookDTO create(BookDTO book) {
		if (book == null ) throw new RequiredObjectIsNullException();
		logger.info("Creating one Book");
		var entity = parseObject(book, Book.class);
		var dto = parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public BookDTO update(BookDTO book) {
		if (book == null ) throw new RequiredObjectIsNullException();
		logger.info("Updating one Book");
		Book entity = repository.findById(book.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		entity.setAuthor(book.getAuthor());
		entity.setDate(book.getDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		var dto = parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one Book");
		Book entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	
	public BookDTO findById(Long id) {
		logger.info("Finding one Book");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var dto = parseObject(entity, BookDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public List<BookDTO> findAll(){ 
		logger.info("Finding all Books");
		var books =  parseListObjects(repository.findAll(), BookDTO.class);
		books.forEach(p -> addHateoasLinks(p));
		return books;
	}
	
	private void addHateoasLinks(BookDTO dto) {
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
		dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
	}

}
