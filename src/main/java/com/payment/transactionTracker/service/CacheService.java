package com.payment.transactionTracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.entity.User;
import com.payment.transactionTracker.repository.UserRepository;


@Service
public class CacheService {

	@Autowired
	UserRepository userRepo;

	@Cacheable
	public List<User> listUsers() {
		List<User> userList = userRepo.findAll();
		return userList;
	}
	
}
