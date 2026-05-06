package com.payment.transactionTracker.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Account {
	@Id
	@GeneratedValue
	String id;
	Double balance;
	String status;
	@ManyToOne
	@JoinColumn(name="userId")
	User user;
}
