package com.digitalmindkr.apirest.data.dto.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;


public class BookDTO extends RepresentationModel<BookDTO> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String author;
	private Date date;
	private BigDecimal price;
	private String title;
	
	public BookDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
