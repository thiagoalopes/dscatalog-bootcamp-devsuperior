package br.dev.thiagoalopes.dscatalog.resources.exceptions;

public class ResourceArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceArgumentException(String message) {
		super(message);
	}
	
	public ResourceArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

}
