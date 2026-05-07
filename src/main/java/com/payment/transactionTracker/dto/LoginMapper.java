package com.payment.transactionTracker.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.model.RegisterRequest;

@Component
public class LoginMapper {

	@Autowired
	PasswordEncoder passwordEncoder;
	public Account accountDto(User user) {
		Account account = new Account();
		account.setStatus("Active");
		account.setUser(user);
		account.setBalance(10000D);
		return account;
	}
	
	public User userDto(RegisterRequest regReq) {
		User user = new User();
		user.setCreatedAt(new Date());
		user.setEmail(regReq.getEmail());
		user.setName(regReq.getName());
		user.setPassword(passwordEncoder.encode(regReq.getPassword()));
		return user;
	}

}
