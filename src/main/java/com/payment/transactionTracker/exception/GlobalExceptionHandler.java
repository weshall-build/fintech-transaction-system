package com.payment.transactionTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<?> accountNotFoundException(AccountNotFoundException accountNotFoundException) {
		return new ResponseEntity<String>(accountNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> insufficientBalanceException(InsufficientBalanceException insufficiantBalanceException) {
		return new ResponseEntity<String>(insufficiantBalanceException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> selfTransferException(SelfTransferRestrictedException selfTransactionException) {
		return new ResponseEntity<String>(selfTransactionException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<?> unauthorizedTransferException(UnauthorizedTransferException unauthException) {
		return new ResponseEntity<String>(unauthException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> userRegistrationException(UserRegistrationAndLoginException userandLoginException){
		return new ResponseEntity<String>(userandLoginException.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
