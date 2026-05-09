package com.payment.transactionTracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.transactionTracker.model.LoginRequest;
import com.payment.transactionTracker.model.RegisterRequest;
import com.payment.transactionTracker.service.AuthService;
import com.payment.transactionTracker.service.JWTService;

import jakarta.validation.Valid;

@RestController
public class LoginController {

	@Autowired
	AuthService authService;
	
	@Autowired
	JWTService jwtService;

	@PostMapping("/auth/register")
	public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterRequest userRequest) {

		if (authService.alreadyRegistered(userRequest)==0l) {
			if (authService.createUser(userRequest)) {
				return new ResponseEntity<>("User Created", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("INTERNAL SERVER ERROR", HttpStatus.REQUEST_TIMEOUT);
			}
		} else {
			return new ResponseEntity<>("USER ALREADY REGISTERED", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest req) {
		if (authService.loginUser(req)) {
			String token = jwtService.generateToken(req.getEmail());
			return new ResponseEntity<>(token, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("INVALID EMAIL OR PASSWORD", HttpStatus.NOT_FOUND);
		}
	}
	

	
	
}
