package com.digitalmindkr.apirest.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalmindkr.apirest.exception.UnsupportedMathOperationException;

@RestController
@RequestMapping("/math")
public class MathController {

	// http://localhost:8080/math/sum/3/5
	@RequestMapping("/sum/{numberOne}/{numberTwo}")
	public Double sum(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception {
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}
	
	@RequestMapping("/sub/{numberOne}/{numberTwo}")
	public Double sub(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return convertToDouble(numberOne) - convertToDouble(numberTwo);
	}
	
	@RequestMapping("/mult/{numberOne}/{numberTwo}")
	public Double mult(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return convertToDouble(numberOne) * convertToDouble(numberTwo);
	}
	
	@RequestMapping("/div/{numberOne}/{numberTwo}")
	public Double div(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return convertToDouble(numberOne) / convertToDouble(numberTwo);
	}
	
	@RequestMapping("/avg/{numberOne}/{numberTwo}")
	public Double avg(@PathVariable("numberOne") String numberOne ,@PathVariable("numberTwo") String numberTwo) throws Exception{
		if(!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return (convertToDouble(numberOne) + convertToDouble(numberTwo))/2;
	}
	
	@RequestMapping("/sqrt/{number}")
	public Double sqrt(@PathVariable("number") String number ) throws Exception{
		if(!isNumeric(number)) throw new UnsupportedMathOperationException("Please set a numeric value correct !!!");
		return Math.sqrt(convertToDouble(number));
	}

	private Double convertToDouble(String strNumber) throws IllegalArgumentException {
		if(strNumber == null || strNumber.isEmpty()) {
			throw new UnsupportedMathOperationException("Please set a numeric value correct !");
		}
		String number = strNumber.replace(",", ".");
		return Double.parseDouble(number);
	}

	private boolean isNumeric(String strNumber) {
		if(strNumber == null || strNumber.isEmpty()) {
			return false;
		}
		String number = strNumber.replace(",", "."); // R$ 5,00 USD 5.00
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
	
}
