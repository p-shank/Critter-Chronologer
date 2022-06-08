package com.udacity.jdnd.course3.critter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pet Not Found")
public class PetNotFound extends RuntimeException {

	public PetNotFound() {
		// TODO Auto-generated constructor stub
	}
	
	public PetNotFound(String message) {
		super(message);
	}

}
