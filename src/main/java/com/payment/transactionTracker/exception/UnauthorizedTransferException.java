package com.payment.transactionTracker.exception;

public class UnauthorizedTransferException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedTransferException(String message) {
		super(message);
	}

}
