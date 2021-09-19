package com.altir.HotelManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyUsedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceAlreadyUsedException(String message) {
		super(message);
	}
}
