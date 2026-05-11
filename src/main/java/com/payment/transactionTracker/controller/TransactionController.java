package com.payment.transactionTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.transactionTracker.model.TransactionRequest;
import com.payment.transactionTracker.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PutMapping("/transfer")
	public ResponseEntity<?> transactMoneyBetweenAccounts(@RequestBody TransactionRequest request,
			SecurityContextHolder contextHolder) throws Exception {
		transactionService.transactionService(request, contextHolder);
		return new ResponseEntity<>("Transaction Done ji", HttpStatus.OK);
	}

}
