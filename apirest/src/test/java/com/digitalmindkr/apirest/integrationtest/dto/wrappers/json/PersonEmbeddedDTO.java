package com.digitalmindkr.apirest.integrationtest.dto.wrappers.json;

import java.io.Serializable;
import java.util.List;

import com.digitalmindkr.apirest.integrationtest.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonEmbeddedDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("people")
	private List<PersonDTO> peoples;

	public PersonEmbeddedDTO() {
	}

	public List<PersonDTO> getPeoples() {
		return peoples;
	}

	public void setPeoples(List<PersonDTO> peoples) {
		this.peoples = peoples;
	}

}
