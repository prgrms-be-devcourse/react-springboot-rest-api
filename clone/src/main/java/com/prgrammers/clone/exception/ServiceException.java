package com.prgrammers.clone.exception;

public class ServiceException {

	public static class NotFoundResourceException extends RuntimeException {

		public NotFoundResourceException(String message) {
			super(message);
		}
	}

}
