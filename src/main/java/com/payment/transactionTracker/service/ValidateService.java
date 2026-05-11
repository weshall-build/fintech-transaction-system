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
//need to change the exception type to something common for both the methods 

	public void checkIfAccountExistsOrNot(Optional<Account> account, boolean isSender) {
		if (account.isEmpty()) {
			if (isSender) {
				throw new AccountNotFoundException("Invalid Account Access");
			} else {
				throw new AccountNotFoundException("Invalid Destination Account");
			}
		}
	}

	public void userValidation(User sendingUser, String emailFromJwt) {
		if (sendingUser != null && sendingUser.getEmail() != null
				&& !sendingUser.getEmail().equalsIgnoreCase(emailFromJwt)) {
			throw new AccountNotFoundException("Invalid Account Access");
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
