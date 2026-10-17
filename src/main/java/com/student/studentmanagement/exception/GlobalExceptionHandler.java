package com.student.studentmanagement.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice	
public class GlobalExceptionHandler 
{
	@ExceptionHandler(StudentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	
	//Map is collection in which data is stored in key and value pair 
	//<String , String> means key and value is string datatype 
	//Map is interface and hashmap implements it (to use its methods , etc.)   
	public Map<String , String> handleStudentNotFound(StudentNotFoundException exception)
	{
		Map<String, String> response = new HashMap<>();
		
		response.put("message" , exception.getMessage());
		
		return response;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationErrors(
	        MethodArgumentNotValidException exception)
	{
	    Map<String, String> response = new HashMap<>();

	    List<FieldError> errors =
	            exception.getBindingResult().getFieldErrors();

	    for(FieldError error : errors)
	    {
	        response.put(
	                error.getField(),
	                error.getDefaultMessage()
	        );
	    }

	    return response;
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleDepartmentNotFound(
	        DepartmentNotFoundException exception)
	{
	    Map<String, String> response = new HashMap<>();

	    response.put("message", exception.getMessage());

	    return response;
	}
	
	@ExceptionHandler(CourseNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String , String> handleCourseNotFound(
			CourseNotFoundException exception)
	{
		Map<String , String> response = new HashMap<>();
		
		response.put("message", exception.getMessage());
		
		return response;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleIllegalArgument(
	        IllegalArgumentException exception) 
	{

	    Map<String, String> response = new HashMap<>();

	    response.put("message", exception.getMessage());

	    return response;
	}
}
