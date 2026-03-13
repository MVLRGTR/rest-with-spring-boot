package com.digitalmindkr.apirest.integrationtest.dto.wrappers.json;

import java.util.List;

import com.digitalmindkr.apirest.integrationtest.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookEmbeddedDTO {
private static final long serialVersionUID = 1L;
	
	@JsonProperty("book")
	private List<BookDTO> books;

	public BookEmbeddedDTO() {
	}

	public List<BookDTO> getbooks() {
		return books;
	}

	public void setbooks(List<BookDTO> books) {
		this.books = books;
	}
}
