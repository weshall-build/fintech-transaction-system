package com.payment.transactionTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.dto.LoginMapper;
import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.model.RegisterRequest;
import com.payment.transactionTracker.repository.AcountRepository;
import com.payment.transactionTracker.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	AcountRepository accRepo;

	@Autowired
	LoginMapper mapper;

	public boolean alreadyRegistered(RegisterRequest userReq) {
		long count = userRepo.countByEmail(userReq.getEmail());
		return count == 0;
	}

	public boolean createUser(RegisterRequest userReq) {
		User user = null;
		try {
			user = mapper.userDto(userReq);
			user = userRepo.save(user);
			log.info("User Created : {} ", user);
			Account account = mapper.accountDto(user);
			accRepo.save(account);
			log.info("Account Created : {} ", account);
			return true;
		} catch (Exception e) {
			log.error("Exception occured while creating the USER : {} ", user);
			return false;
		}

	}
}
