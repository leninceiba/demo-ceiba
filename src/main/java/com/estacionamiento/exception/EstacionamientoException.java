package com.estacionamiento.exception;

public class EstacionamientoException extends Exception{

	private static final long serialVersionUID = 1L;

	public EstacionamientoException(String error){
		super( error);
	}	
}
