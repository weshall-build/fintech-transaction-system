package com.payment.transactionTracker.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.payment.transactionTracker.exception.UnauthorizedTransferException;

@Service
public class FraudDetectionService {

	Map<String, Queue<Long>> userAndTimstampsOfTransation = new HashMap<String, Queue<Long>>();

	public void checkLastSetOfTransactions(String email) {

		Queue<Long> timeStamp = userAndTimstampsOfTransation.get(email);
		if (timeStamp == null) {
			timeStamp = new LinkedList<Long>();
			Long currentTImeStamp = System.currentTimeMillis();
			timeStamp.add(currentTImeStamp);
			userAndTimstampsOfTransation.put(email, timeStamp);
		} else {
			Long currentTImeStamp = System.currentTimeMillis();
			while (!timeStamp.isEmpty() && currentTImeStamp - timeStamp.peek() >= 60000) {
				timeStamp.poll();
			}
			timeStamp.add(currentTImeStamp);
			userAndTimstampsOfTransation.put(email, timeStamp);
		}

		if (timeStamp.size() >= 5) {
			throw new UnauthorizedTransferException("cant process the transaction as its been constinous");
		}

	}
}
