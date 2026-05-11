package com.payment.transactionTracker.dto;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.TransactionHistory;
import com.payment.transactionTracker.model.TransactionRequest;
import com.payment.transactionTracker.repository.AcountRepository;

@Component
public class TransactionDto {
	@Autowired
	AcountRepository accountRepo;

	public boolean transactionFromSenderToReceiver(TransactionRequest transactionReq, Account senderAccount,
			Account receiverAccount) {
		double senderLeftMoney = senderAccount.getBalance() - transactionReq.getAmount();
		double receiverGainMoney = receiverAccount.getBalance() + transactionReq.getAmount();
		senderAccount.setBalance(senderLeftMoney);
		receiverAccount.setBalance(receiverGainMoney);
		return true;
	}

	public TransactionHistory transactionHistoryDto(TransactionRequest request) {
		Optional<Account> account = accountRepo.findById(request.getFromAcountId());
		Optional<Account> receiverAccount = accountRepo.findById(request.getToAccountId());
		TransactionHistory tHistory = new TransactionHistory();
		tHistory.setAmount(request.getAmount() != null ? request.getAmount() : null);
		tHistory.setCreateAt(new Date());
		tHistory.setSenderAccountId(request.getFromAcountId() != null ? request.getFromAcountId() : null);
		tHistory.setReceiverAccountId(request.getToAccountId() != null ? request.getToAccountId() : null);
		tHistory.setSenderEmail(!account.isEmpty() ? account.get().getUser().getEmail() : null);
		tHistory.setReceiverEmail(!receiverAccount.isEmpty() ? receiverAccount.get().getUser().getEmail() : null);
		if (tHistory.getSenderEmail() != null && tHistory.getReceiverEmail() != null
				&& tHistory.getSenderEmail().equals(tHistory.getReceiverEmail())) {
			tHistory.setSelfTransaction(true);
		} else {
			tHistory.setSelfTransaction(false);
		}

		return tHistory;
	}

}
