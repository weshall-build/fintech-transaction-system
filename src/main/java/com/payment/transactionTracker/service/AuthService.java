package com.payment.transactionTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.dto.LoginMapper;
import com.payment.transactionTracker.entity.Account;
import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.exception.UserRegistrationAndLoginException;
import com.payment.transactionTracker.model.LoginRequest;
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

	@Autowired
	PasswordEncoder passwordEncoder;
	
	public long alreadyRegistered(RegisterRequest userReq) {
		 return userRepo.countByEmail(userReq.getEmail());
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
			log.error("Exception occured while creating the USER : {} , Exception e : {} ", user,e);
			return false;
		}
	}
	
	public void loginUser(LoginRequest reqq) {
		User user = userRepo.findByEmail(reqq.getEmail());
		if (user == null) {
			throw new UserRegistrationAndLoginException("Invalid missing email or password");
		}
		if (!passwordEncoder.matches(reqq.getPassword(), user.getPassword())) {
			throw new UserRegistrationAndLoginException("Invalid missing email or password");
		}
	}
}
