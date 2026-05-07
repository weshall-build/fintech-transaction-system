package com.payment.transactionTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.transactionTracker.entity.Account;

@Repository
public interface AcountRepository extends JpaRepository<Account, Integer> {

	List<Account> findAll();
	
}
