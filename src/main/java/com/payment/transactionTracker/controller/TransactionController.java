package com.payment.transactionTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.transactionTracker.entity.TransactionHistory;
import com.payment.transactionTracker.model.TransactionRequest;
import com.payment.transactionTracker.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@PutMapping("/transfer")
	public ResponseEntity<?> transactMoneyBetweenAccounts(@RequestBody TransactionRequest request,
			SecurityContextHolder contextHolder) throws Exception {
		transactionService.processTransactionAndSaveHistory(request, contextHolder);
		return new ResponseEntity<>("Transaction Done ji", HttpStatus.OK);
	}

	@GetMapping("/transactions/{accountId}")
	public ResponseEntity<?> getTransactionsByUser(SecurityContextHolder contextHolder,
			@PathVariable("accountId") Integer accountId) {
		List<TransactionHistory> transactionHistoryList = transactionService
				.fetchTransactionHistoryBasedOnAcountId(accountId, contextHolder);
		return new ResponseEntity<>(transactionHistoryList, HttpStatus.OK);
	}

}
