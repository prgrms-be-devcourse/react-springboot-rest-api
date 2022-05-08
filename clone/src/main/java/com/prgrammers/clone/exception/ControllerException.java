package com.prgrammers.clone.exception;

public class ControllerException {

	public static class ValidationException extends IllegalArgumentException{
		public ValidationException(String message) {
			super(message);
		}
	}
}
