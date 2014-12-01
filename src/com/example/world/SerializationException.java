/*
 * SerializationException.java
 * 
 * 
 * @author ada
 * @version 1.0  2008Äê10ÔÂ25ÈÕ
 */
package com.example.world;

/**
 *
 */
public class SerializationException extends Exception {

	private static final long serialVersionUID = -1229514702218596466L;

	/**
	 * 
	 */
	public SerializationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SerializationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SerializationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SerializationException(Throwable cause) {
		super(cause);
	}

	
}
