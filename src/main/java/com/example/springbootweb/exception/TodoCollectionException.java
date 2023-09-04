package com.example.springbootweb.exception;

public class TodoCollectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TodoCollectionException(String message){
		super(message);
	}
	
	public static String NotFoundException(String id) {
		return "Todo with "+ id + " not Found";
	}
	public static String TodoAlreadyExistException(String name) {
		return "Todo with given name "+ name +" already exist.";
	}
}
