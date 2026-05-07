package com.payment.transactionTracker.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class User {
	
	@Id
	@GeneratedValue
	Integer userId;
	String name;
	String email;
	String password;
	Date createdAt;
}
