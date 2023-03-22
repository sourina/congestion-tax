package com.traffic.cars.congestion.taxcalculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * * ExceptionHandler will intercept all the exceptions retuning from application and through it as a meaningful response entity over HTTP.
 * Single place to handle exceptions conveniently
 */
@Slf4j
@ControllerAdvice
public class RequestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<RequestErrorResponse> handleException(ResponseStatusException exc) {
		RequestErrorResponse error = new RequestErrorResponse(exc.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, exc.getStatus());
	}
}
