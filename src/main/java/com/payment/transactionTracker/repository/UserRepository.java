package com.payment.transactionTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.transactionTracker.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	long countByEmail(String email);
	
	User findByEmail(String email);

	List<User> findAll();
}
