package com.digitalmindkr.apirest.integrationtest.dto.wrappers;

import java.io.Serializable;
import java.util.List;

import com.digitalmindkr.apirest.integrationtest.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedModelBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @JacksonXmlElementWrapper(localName = "content")
    @JacksonXmlProperty(localName = "content")
    private List<BookDTO> content;

    public List<BookDTO> getContent() {
        return content;
    }

    public void setContent(List<BookDTO> content) {
        this.content = content;
    }
}
