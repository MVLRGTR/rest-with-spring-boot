package com.digitalmindkr.apirest.data.dto.v1;

import java.io.Serializable;
//import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.digitalmindkr.apirest.serializer.GenderSerializer;

//@JsonPropertyOrder({"id","address","first_name","last_name","gender"})
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	//private String sensitiveData;
	//@JsonProperty("first_name")
	private String firstName;
	//@JsonProperty("last_name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String lastName;
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	//private String phoneNumber;
	//@JsonFormat(pattern = "dd/MM/yyyy")
	//private Date birthDay;
	private String address;
	//@JsonIgnore
	@JsonSerialize(using = GenderSerializer.class)
	private String gender;
	
	public PersonDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, gender, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonDTO other = (PersonDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName);
	}

	
}
