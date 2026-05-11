package com.payment.transactionTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.dto.TransactionDto;
import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.TransactionHistory;
import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.exception.AccountNotFoundException;
import com.payment.transactionTracker.exception.InsufficientBalanceException;
import com.payment.transactionTracker.exception.SelfTransferRestrictedException;
import com.payment.transactionTracker.exception.UnauthorizedTransferException;
import com.payment.transactionTracker.model.TransactionRequest;
import com.payment.transactionTracker.repository.AcountRepository;
import com.payment.transactionTracker.repository.TransactionHistoryRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

	@Autowired
	AcountRepository accountRepo;

	@Autowired
	TransactionDto transactionDto;

	@Autowired
	TransactionHistoryRepository historyRepo;

	@Autowired
	ValidateService valService;

	@Autowired
	FraudDetectionService fraudService;
	
	@SuppressWarnings("static-access")
	@Transactional
	public void processTransaction(TransactionRequest request, SecurityContextHolder context) {

		String emailFromJwt = context.getContext().getAuthentication().getName();
		Optional<Account> senderAccount = accountRepo.findById(request.getFromAcountId());
		Optional<Account> receiverAccount = accountRepo.findById(request.getToAccountId());

		valService.checkIfAccountExistsOrNot(senderAccount, true);
		valService.checkIfAccountExistsOrNot(receiverAccount, false);
		User sendingUser = senderAccount.get().getUser();
		valService.userValidation(sendingUser, emailFromJwt);
		valService.validateSelfTransfer(request);
		valService.validateAmount(request, senderAccount);

		fraudService.checkLastSetOfTransactions(emailFromJwt);
		transactionDto.transactionFromSenderToReceiver(request, senderAccount.get(), receiverAccount.get());
		accountRepo.save(senderAccount.get());
		accountRepo.save(receiverAccount.get());

	}

	public void processTransactionAndSaveHistory(TransactionRequest request, SecurityContextHolder context)
			throws Exception {
		try {
			this.processTransaction(request, context);
			TransactionHistory history = transactionDto.transactionHistoryDto(request);
			history.setStatus("SUCCESS");
			historyRepo.save(history);
		} catch (Exception e) {
			TransactionHistory history = transactionDto.transactionHistoryDto(request);
			history.setStatus("FAIL");
			history.setFailureReason(e.getMessage());
			historyRepo.save(history);
			throw new Exception(e);
		}

	}

	public List<TransactionHistory> fetchTransactionHistoryBasedOnAcountId(Integer accountId,
			SecurityContextHolder contextHolder) {

		@SuppressWarnings("static-access")
		String emailFromJwt = contextHolder.getContext().getAuthentication().getName();
		Optional<Account> currentAccount = accountRepo.findById(accountId);
		valService.checkIfAccountExistsOrNot(currentAccount, true);
		User currentUser = currentAccount.get().getUser();
		valService.userValidation(currentUser, emailFromJwt);
		List<TransactionHistory> listOftransactionHistoryByAcountId = historyRepo
				.findBySenderAccountIdOrReceiverAccountId(accountId,accountId);
		return listOftransactionHistoryByAcountId;
	}
}
