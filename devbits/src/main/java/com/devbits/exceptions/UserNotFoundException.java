package com.devbits.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends RuntimeException{

	private String message;
	
}
