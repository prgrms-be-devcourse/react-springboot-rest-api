package com.prgrammers.clone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	private static final String LOG_PREFIX = "error message : {}";

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ServiceException.NotFoundResourceException.class)
	public ResponseEntity<String> handleNotFoundResourceException(ServiceException.NotFoundResourceException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.notFound()
				.build();
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = DomainException.InventoryException.class)
	public ResponseEntity<String> handleInventoryException(DomainException.InventoryException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.badRequest()
				.body("수량을 한번 점검해주세요...");
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ControllerException.ValidationException.class)
	public ResponseEntity<String> handleIllegalArgumentException(ControllerException.ValidationException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.badRequest()
				.body("잘못된 값을 입력하였습니다.");
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = JdbcException.NotFoundDomain.class)
	public ResponseEntity<String> handleJdbcExceptionNotFoundDomainException(JdbcException.NotFoundDomain e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.badRequest()
				.body("잘못된 값을 입력했습니다..");
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = DomainException.NotProperCancelPolicy.class)
	public ResponseEntity<String> handleDomainExceptionNotProperCancelPolicy(DomainException.NotProperCancelPolicy e){
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.badRequest()
				.body("배송 전인 상태에서만 주문 취소가 가능합니다.");
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = JdbcException.NotExecuteQuery.class)
	public ResponseEntity<String> handleJdbcNotExecuteQueryException(JdbcException.NotExecuteQuery e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.internalServerError()
				.body("서버 내부에 문제가 발생했습니다... ");
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		log.warn(LOG_PREFIX, e.getMessage());
		return ResponseEntity.internalServerError()
				.body("서버 내부에 문제가 발생했습니다... ");
	}
}
