package com.digitalmindkr.apirest.services;

import static com.digitalmindkr.apirest.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.controllers.PersonController;
import com.digitalmindkr.apirest.controllers.TestLogController;
import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;
import com.digitalmindkr.apirest.exception.RequiredObjectIsNullException;
import com.digitalmindkr.apirest.exception.ResourceNotFoundException;
import com.digitalmindkr.apirest.model.Person;
import com.digitalmindkr.apirest.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

	//private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	PersonRepository repository;
	@Autowired
	PagedResourcesAssembler<PersonDTO> assembler;

	private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName()); //faço a adição de um logger para a classe
	
	public PersonDTO create(PersonDTO person) {
		if (person == null ) throw new RequiredObjectIsNullException();
		logger.info("Creating one person");
		var entity = parseObject(person, Person.class);
		@SuppressWarnings("null")
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	public PersonDTO update(PersonDTO person) {
		if (person == null ) throw new RequiredObjectIsNullException();
		logger.info("Updating one person");
		@SuppressWarnings("null")
		Person entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		var dto = parseObject(repository.save(entity), PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	@Transactional //faço a adição dessa anotation para ela ter os requisitos ACID já que esse recurso não exite por padrão no JPA
	public PersonDTO disablePerson(@NonNull Long id) {
		logger.info("Disabling one person");
		repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.disablePerson(id);
		var entity = repository.findById(id).get();
		var dto = parseObject(entity, PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
	}
	
	@SuppressWarnings("null")
	public void delete(@NonNull Long id) {
		logger.info("Deleting one person");
		Person entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	
	public PersonDTO findById(@NonNull Long id) {
		logger.info("Finding one person");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var dto = parseObject(entity, PersonDTO.class);
		addHateoasLinks(dto);
		return dto;
		
	}

	@SuppressWarnings("null")
	public PagedModel<EntityModel<PersonDTO>> findAll(@NonNull Pageable pageable){ 
		logger.info("Finding all peoples");
		
		var people = repository.findAll(pageable);
		var peopleWithLinks = people.map((Person person) -> {
			var dto = parseObject(person, PersonDTO.class);
			addHateoasLinks(dto);
			return dto;
		});
		
		 Link findAllLink = WebMvcLinkBuilder.linkTo(
		            WebMvcLinkBuilder.methodOn(PersonController.class)
		                .findAll(
		                    pageable.getPageNumber(),
		                    pageable.getPageSize(),
		                    String.valueOf(pageable.getSort())))
		                .withSelfRel();
		    return assembler.toModel(peopleWithLinks, findAllLink);
	}
	
	@SuppressWarnings("null")
	private void addHateoasLinks(PersonDTO dto) {
		if (dto == null) return;
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));
		dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
		dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
		dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
	}
	
}
