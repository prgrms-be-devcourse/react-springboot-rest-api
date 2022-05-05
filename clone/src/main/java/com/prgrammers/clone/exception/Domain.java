package com.prgrammers.clone.exception;

public class Domain {
	public static class NotValidException extends RuntimeException {
		public NotValidException(String message) {
			super(message);
		}
	}
}
