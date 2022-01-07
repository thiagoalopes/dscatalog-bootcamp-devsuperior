package br.dev.thiagoalopes.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.dev.thiagoalopes.dscatalog.services.exceptions.DatabaseException;
import br.dev.thiagoalopes.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundException(
			ResourceNotFoundException e, HttpServletRequest request, HttpServletResponse response) {
		
		StandardError error = new StandardError(
					Instant.now(),
					HttpStatus.NOT_FOUND.value(),
					"Resource not found",
					e.getMessage(),
					request.getRequestURI()
				);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> resourceNotFoundException(
			DatabaseException e, HttpServletRequest request, HttpServletResponse response) {
		
		StandardError error = new StandardError(
					Instant.now(),
					HttpStatus.BAD_REQUEST.value(),
					"Database exception",
					e.getMessage(),
					request.getRequestURI()
				);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(error);
	}
}
