package com.payment.transactionTracker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.exception.AccountNotFoundException;
import com.payment.transactionTracker.exception.InsufficientBalanceException;
import com.payment.transactionTracker.exception.SelfTransferRestrictedException;
import com.payment.transactionTracker.exception.UnauthorizedTransferException;
import com.payment.transactionTracker.model.TransactionRequest;

@Service
public class ValidateService {
	public void validateBasicAccountInfo(Optional<Account> senderAccount, Optional<Account> receiverAccount) {
		if (senderAccount.isEmpty()) {
			throw new AccountNotFoundException("sender acount not found");
		}
		if (receiverAccount.isEmpty()) {
			throw new AccountNotFoundException("receiver acount not found");
		}
	}

	public void userValidation(User sendingUser, String emailFromJwt) {
		if (sendingUser != null && sendingUser.getEmail() != null
				&& !sendingUser.getEmail().equalsIgnoreCase(emailFromJwt)) {
			throw new UnauthorizedTransferException("this account Doesnot belongs to you");
		}
	}

	public void validateSelfTransfer(TransactionRequest request) {
		if (request.getFromAcountId().equals(request.getToAccountId())) {
			throw new SelfTransferRestrictedException("cant transfer to yourself");
		}
	}

	public void validateAmount(TransactionRequest request, Optional<Account> senderAccount) {
		if (request.getAmount() <= 0) {
			throw new InsufficientBalanceException("BKL GAREEB");
		}
		if (request.getAmount() > senderAccount.get().getBalance()) {
			throw new InsufficientBalanceException("paise illa");
		}
	}

}
