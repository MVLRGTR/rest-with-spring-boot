package com.digitalmindkr.apirest.file.export.contract;

import java.util.List;

import org.springframework.core.io.Resource;

import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;

public interface FileExporter {
	
	Resource exportFile(List<PersonDTO> people) throws Exception;

}
