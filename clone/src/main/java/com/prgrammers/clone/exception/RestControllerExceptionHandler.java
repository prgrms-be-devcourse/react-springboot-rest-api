package com.prgrammers.clone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	@ExceptionHandler(value = ServiceException.NotFoundResource.class)
	public ResponseEntity<String> handleNotFoundResource(ServiceException.NotFoundResource e) {
		log.warn("error message : {}", e.getMessage());
		return ResponseEntity.notFound()
				.build();
	}
}
