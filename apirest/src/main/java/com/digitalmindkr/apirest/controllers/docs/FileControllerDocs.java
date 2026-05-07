package com.digitalmindkr.apirest.controllers.docs;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.digitalmindkr.apirest.data.dto.v1.FileUploadResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {
	default FileUploadResponseDTO uploadFile(MultipartFile file) {
		return null;
	}
    default List<FileUploadResponseDTO> uploadMultipleFiles(MultipartFile[] files) {
		return null;
	}
    default ResponseEntity<Resource> downloadFile(String fileName,HttpServletRequest request) {
		return null;
	}

}
