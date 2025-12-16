package com.digitalmindkr.apirest.request.converters;

import com.digitalmindkr.apirest.exception.UnsupportedMathOperationException;

public class NumberConverter {
	
	public static double converterToDouble(String strNumber) {
		if(strNumber == null || strNumber.isEmpty()) {
			throw new UnsupportedMathOperationException("Please set a numeric value correct !!!!");
		}
		String number = strNumber.replace(",", ".");
		return Double.parseDouble(number);
	}
	
	public static boolean isNumeric(String strNumber) {
		if(strNumber == null || strNumber.isEmpty()) {
			throw new UnsupportedMathOperationException("Please set a numeric value correct !!!!");
		}
		String number = strNumber.replace(",", "."); // R$ 5,00 USD 5.00
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
