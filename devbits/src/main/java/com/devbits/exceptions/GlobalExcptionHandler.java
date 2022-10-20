package com.devbits.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> entityNotFound(HttpServletResponse response, UserNotFoundException ex) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
				Arrays.asList(ex.getMessage()));

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
