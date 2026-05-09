package com.payment.transactionTracker.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

	Integer fromAcountId;
	Integer toAccountId;
	Double amount;
}
