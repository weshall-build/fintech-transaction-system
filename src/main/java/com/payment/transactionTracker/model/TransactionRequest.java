package com.payment.transactionTracker.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {

    @NotBlank(message = "fromAccountID is Required")
	Integer fromAcountId;
    @NotBlank(message = "toAcountID is Required")
	Integer toAccountId;
	Double amount;
}
