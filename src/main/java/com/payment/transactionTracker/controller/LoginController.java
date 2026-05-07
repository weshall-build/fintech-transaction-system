package com.payment.transactionTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.payment.transactionTracker.model.RegisterRequest;
import com.payment.transactionTracker.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class LoginController {

	@Autowired
	AuthService authService;

	@PostMapping("/auth/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest userRequest) {

		if (authService.alreadyRegistered(userRequest)) {
			if (authService.createUser(userRequest)) {
				return new ResponseEntity<>("User Created", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.REQUEST_TIMEOUT);
			}
		} else {
			return new ResponseEntity<>("USER ALREADY REGISTERED", HttpStatus.BAD_REQUEST);
		}
	}
}
