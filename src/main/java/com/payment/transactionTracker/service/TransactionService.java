package com.payment.transactionTracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.dto.TransactionDto;
import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.TransactionHistory;
import com.payment.transactionTracker.entity.User;
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

	@SuppressWarnings("static-access")
	@Transactional
	public boolean processTransaction(TransactionRequest request, SecurityContextHolder context) {
		try {

			String emailFromJwt = context.getContext().getAuthentication().getName();
			Optional<Account> senderAccount = accountRepo.findById(request.getFromAcountId());
			Optional<Account> receiverAccount = accountRepo.findById(request.getToAccountId());
			User sendingUser = senderAccount.get().getUser();
			if (request.getFromAcountId() == request.getToAccountId()) {
				return false;
			}
			if (sendingUser != null && sendingUser.getEmail() != null
					&& !sendingUser.getEmail().equalsIgnoreCase(emailFromJwt)) {
				return false;
			}
			if (request.getAmount() > senderAccount.get().getBalance()) {
				return false;
			}

			transactionDto.transactionFromSenderToReceiver(request, senderAccount.get(), receiverAccount.get());
			accountRepo.save(senderAccount.get());
			accountRepo.save(receiverAccount.get());

			return true;
		} catch (Exception e) {
			log.error("EXCEPTION OCCURED WHILE TRANSFER FROM ONE ACCOUNT TO ANOTHER");
			return false;
		}
	}
	
	public boolean transactionService(TransactionRequest request, SecurityContextHolder context) {

		boolean success=this.processTransaction(request,context);
		if(success) {
			TransactionHistory history=transactionDto.transactionHistoryDto(request);
			history.setStatus("SUCCESS");
			historyRepo.save(history);
		}else {
			TransactionHistory history=transactionDto.transactionHistoryDto(request);
			history.setStatus("FAIL");
			historyRepo.save(history);
		}
		return true;
	}
}
