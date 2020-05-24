package com.hust.projectmanagement.projectservice.controlleradvice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.hust.projectmanagement.projectservice.exception.ErrorDetails;
import com.hust.projectmanagement.projectservice.exception.NotAuthorizeActionException;
import com.hust.projectmanagement.projectservice.exception.ProjectNotFoundException;
import com.hust.projectmanagement.projectservice.exception.UserNotFoundException;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
private static final Logger logger = LoggerFactory.getLogger(RestControllerAdvice.class);
	
	@ExceptionHandler(SQLException.class)
	public String handleSQLException(HttpServletRequest request, Exception ex){
		logger.info("SQLException Occured:: URL="+request.getRequestURL());
		return "database_error";
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(Exception e){
		logger.error("IOException handler executed");
		e.printStackTrace();
		//returning 404 error code
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	@ExceptionHandler(ProjectNotFoundException.class)
	  public final ResponseEntity<ErrorDetails> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	@ExceptionHandler(UserNotFoundException.class)
	  public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	@ExceptionHandler(NotAuthorizeActionException.class)
	  public final ResponseEntity<?> handleNotAuthorizeActionFoundException(NotAuthorizeActionException ex, WebRequest request) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	  }
}
