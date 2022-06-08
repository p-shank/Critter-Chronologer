package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee Not Found")
public class EmployeeNotFound extends RuntimeException{

	public EmployeeNotFound() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeNotFound(String message) {
		super(message);
	}

}
