package com.github.winneonsword.MM.exceptions;

import java.util.logging.Level;

import org.bukkit.Bukkit;

@SuppressWarnings("serial")
public class InvalidClassException extends Exception {
	
	public InvalidClassException(){
		
		super();
		
	}
	
	public InvalidClassException(String message){
		
		super(message);
		Bukkit.getLogger().log(Level.SEVERE, message + " is not a valid class!");
		
	}
	
	public InvalidClassException(Throwable cause){
		
		super(cause);
		
	}
	
	public InvalidClassException(String message, Throwable cause){
		
		super(message, cause);
		
	}
	
}
