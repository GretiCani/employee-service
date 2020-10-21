package com.icub.task.employee.gateway;

import com.icub.task.employee.commons.exception.*;
import lombok.extern.log4j.Log4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EmployeeControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<Object> handleEmployeeNotFound(EmployeeNotFoundException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
        log.warn("EmployeeNotFoundException: "+exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());

    }

    @ExceptionHandler(ProjectNotFoundException.class)
    protected ResponseEntity<Object> handleEmployeeNotFound(ProjectNotFoundException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
        log.warn("ProjectNotFoundException: "+exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse,exceptionResponse.getStatus());

    }

    @ExceptionHandler(UnsupportedEventTypeException.class)
    protected ResponseEntity<Object> unsupportedEventTypeException(UnsupportedEventTypeException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
        log.warn("UnsupportedEventTypeException: "+exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse,exceptionResponse.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"media type is not supported."), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST,getRequiredFields(ex));
        log.warn("MethodArgumentNotValidException: "+exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }


    private Map<String,String> getRequiredFields(MethodArgumentNotValidException ex){
        Map<String,String> errors= new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e ->{
            errors.put(e.getField(),e.getDefaultMessage());
        });
        return errors;
    }



}
