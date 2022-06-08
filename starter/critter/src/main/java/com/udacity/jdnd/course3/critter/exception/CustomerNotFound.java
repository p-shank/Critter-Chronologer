package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer Not Found")
public class CustomerNotFound extends RuntimeException{

	public CustomerNotFound() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerNotFound(String message) {
		super(message);
	}

}
