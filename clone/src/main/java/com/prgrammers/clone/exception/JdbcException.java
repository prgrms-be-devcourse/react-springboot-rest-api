package com.prgrammers.clone.exception;

public class JdbcException {

	public static class NotExecuteQuery extends RuntimeException {
		public NotExecuteQuery(String message) {
			super(message);
		}
	}
}
