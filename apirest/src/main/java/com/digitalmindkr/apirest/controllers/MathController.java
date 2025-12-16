package com.digitalmindkr.apirest.controllers;

import com.digitalmindkr.apirest.request.converters.NumberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalmindkr.apirest.exception.UnsupportedMathOperationException;
import com.digitalmindkr.apirest.math.Operations;

@RestController
@RequestMapping("/math")
public class MathController {
	
	private Operations math = new Operations();
	
	// http://localhost:8080/math/sum/3/5
	@RequestMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception {
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.sum(NumberConverter.converterToDouble(numberOne),NumberConverter.converterToDouble(numberTwo));
	}
	
	@RequestMapping("/sub/{numberOne}/{numberTwo}")
	public Double sub(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.sub(NumberConverter.converterToDouble(numberOne), NumberConverter.converterToDouble(numberTwo));
	}
	
	@RequestMapping("/mult/{numberOne}/{numberTwo}")
	public Double mult(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.mult(NumberConverter.converterToDouble(numberOne), NumberConverter.converterToDouble(numberTwo));
	}
	
	@RequestMapping("/div/{numberOne}/{numberTwo}")
	public Double div(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.div(NumberConverter.converterToDouble(numberOne), NumberConverter.converterToDouble(numberTwo));
	}
	
	@RequestMapping("/avg/{numberOne}/{numberTwo}")
	public Double avg(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.avg(NumberConverter.converterToDouble(numberOne), NumberConverter.converterToDouble(numberTwo));
	}
	
	@RequestMapping("/sqrt/{number}")
	public Double sqrt(@PathVariable("number") String number ) throws Exception{
		if(!NumberConverter.isNumeric(number)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return math.sqrt(NumberConverter.converterToDouble(number));
	}


	
}
