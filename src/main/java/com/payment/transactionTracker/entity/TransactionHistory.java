package com.payment.transactionTracker.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Getter;
import jakarta.persistence.Id;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class TransactionHistory {
	@Id
	@GeneratedValue
	Integer transactionId;
	String senderEmail;
	String receiverEmail;
	boolean selfTransaction;
	Integer senderAccountId;
	Integer receiverAccountId;
	Double amount;
	Date createAt;
	String status;
	String failureReason;
}
