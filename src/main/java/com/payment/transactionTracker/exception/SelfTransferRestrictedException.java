package com.payment.transactionTracker.exception;

public class SelfTransferRestrictedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SelfTransferRestrictedException(String message) {
		super(message);
	}

}
