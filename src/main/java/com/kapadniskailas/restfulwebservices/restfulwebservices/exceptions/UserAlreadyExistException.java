/**
 * 
 */
package com.kapadniskailas.restfulwebservices.restfulwebservices.exceptions;

/**
 * @author kailaskapadnis
 *
 */
public class UserAlreadyExistException extends RuntimeException {
	public UserAlreadyExistException(String message) {
		super(message);
	}
}
