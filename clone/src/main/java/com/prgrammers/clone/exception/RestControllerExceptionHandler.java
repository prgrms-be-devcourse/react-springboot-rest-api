package com.prgrammers.clone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	@ExceptionHandler(value = ServiceException.NotFoundResourceException.class)
	public ResponseEntity<String> handleNotFoundResourceException(ServiceException.NotFoundResourceException e) {
		log.warn("error message : {}", e.getMessage());
		return ResponseEntity.notFound()
				.build();
	}

	@ExceptionHandler(value = DomainException.InventoryException.class)
	public ResponseEntity<String> handleInventoryException(DomainException.InventoryException e) {
		log.warn("error message : {}", e.getMessage());
		return ResponseEntity.badRequest()
				.build();
	}
}
