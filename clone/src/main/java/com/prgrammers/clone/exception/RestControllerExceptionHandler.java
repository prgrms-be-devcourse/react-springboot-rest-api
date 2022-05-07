package com.prgrammers.clone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	private static final String LOG_PREFIX = "error message : {}";

	@ExceptionHandler(value = ServiceException.NotFoundResourceException.class)
	public ResponseEntity<String> handleNotFoundResourceException(ServiceException.NotFoundResourceException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.notFound()
				.build();
	}

	@ExceptionHandler(value = DomainException.InventoryException.class)
	public ResponseEntity<String> handleInventoryException(DomainException.InventoryException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.badRequest()
				.build();
	}

	@ExceptionHandler(value = JdbcException.NotExecuteQuery.class)
	public ResponseEntity<String> handleJdbcNotExecuteQueryException(JdbcException.NotExecuteQuery e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.internalServerError()
				.build();
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.internalServerError()
				.build();
	}


}
