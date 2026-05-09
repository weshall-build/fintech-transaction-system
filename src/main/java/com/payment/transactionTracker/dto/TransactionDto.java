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
		tHistory.setAmount(request.getAmount());
		tHistory.setCreateAt(new Date());
		tHistory.setSenderAccountId(request.getFromAcountId());
		tHistory.setReceiverAccountId(request.getToAccountId());
		tHistory.setSenderEmail(account.get().getUser().getEmail());
		tHistory.setReceiverEmail(receiverAccount.get().getUser().getEmail());
		if (tHistory.getSenderEmail().equals(tHistory.getReceiverEmail())) {
			tHistory.setSelfTransaction(true);
		} else {
			tHistory.setSelfTransaction(false);
		}

		return tHistory;
	}

}
