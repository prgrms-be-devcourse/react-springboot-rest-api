package com.prgrammers.clone.exception;

public class DomainException {
	public static class NotValidException extends RuntimeException {
		public NotValidException(String message) {
			super(message);
		}
	}

	public static class InventoryException extends RuntimeException {
		public InventoryException(String message) {
			super(message);
		}
	}

}
