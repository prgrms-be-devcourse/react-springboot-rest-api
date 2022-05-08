package com.prgrammers.clone.exception;

public class JdbcException {

	public static class NotExecuteQuery extends RuntimeException {
		public NotExecuteQuery(String message) {
			super(message);
		}
	}

	public static class NotFoundDomain extends RuntimeException {
		public NotFoundDomain(String message) {
			super(message);
		}
	}
}
