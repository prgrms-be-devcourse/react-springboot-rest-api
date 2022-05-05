package com.prgrammers.clone.exception;

public class ServiceException {

	public static class NotFoundResource extends RuntimeException {

		public NotFoundResource(String message) {
			super(message);
		}
	}

}
