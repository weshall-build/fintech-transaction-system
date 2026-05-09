package com.payment.transactionTracker.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.transactionTracker.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	@Autowired
	UserRepository userRepo;
	
	public static final String SECRET_KEY = "mySuperSecretKeyForJwtAuthentication123456";

	public String generateToken(String email) {

		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignInKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean checkIfEmailExists(String email) {
		return userRepo.countByEmail(email) == 1;
	}
	
	public boolean checkifTheTokenIsExpired(String token) {
		Date date = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody()
				.getExpiration();
		return date.before(new Date());
	}
}
