package com.digitalmindkr.apirest.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.digitalmindkr.apirest.data.dto.v2.PersonDTOv2;
import com.digitalmindkr.apirest.model.Person;

@Service
public class PersonMapper {
	
	public PersonDTOv2 convertEntityToDTO(Person person) {
		PersonDTOv2 dto = new PersonDTOv2();
		dto.setId(person.getId());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setAddress(person.getAddress());
		dto.setGender(person.getGender());
		dto.setBirthDay(new Date());
		return dto;
	}
	
	public Person convertDTOToEntity(PersonDTOv2 person) {
		Person dto = new Person();
		dto.setId(person.getId());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setAddress(person.getAddress());
		dto.setGender(person.getGender());
		//dto.setBirthDay(new Date());
		return dto;
	}
}
