/**
 * 
 */
package com.kapadniskailas.restfulwebservices.restfulwebservices.beans;

/**
 * @author kailaskapadnis
 *
 */
public class HelloWorldBean {
	
	private String message;

	public HelloWorldBean(String message) {
		super();
		this.message = message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	
	
	
}
