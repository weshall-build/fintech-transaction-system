package com.payment.transactionTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.transactionTracker.entity.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
	List<TransactionHistory> findBySenderAccountIdOrReceiverAccountId(Integer accountId,Integer accountId2);
}
