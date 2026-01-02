package com.digitalmindkr.apirest.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.digitalmindkr.apirest.exception.ExceptionResponse;
import com.digitalmindkr.apirest.exception.ResourceNotFoundException;

@RestControllerAdvice     //Caso alguma classe não forneça tratamento adequado para o erro ele irá cair no tratamento global
@RestController           //Aqui nos temos o handler que irá fazer o direcionamento dos erros para o tratamento correto

public class CustomEntityResponseHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class) // aqui fazemos o tratamento global de excessões
	public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex , WebRequest request){
		ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class) // aqui fazemos o tratamento de parametros não suportados 
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex , WebRequest request){
		ExceptionResponse response = new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
}
