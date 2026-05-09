package com.payment.transactionTracker.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.payment.transactionTracker.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		boolean startsWithBearer = header!=null && (!header.isBlank()) && header.startsWith("Bearer ");
		boolean emailExists = false;
		boolean checkifExpired = true;
		String email = null;
		if (startsWithBearer) {
			String token = header.split(" ")[1];
			email = jwtService.extractEmail(header.split(" ")[1]);
			emailExists = jwtService.checkIfEmailExists(email);
			checkifExpired = jwtService.checkifTheTokenIsExpired(token);
		}
		if (emailExists && !checkifExpired) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
					null,null);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);

	}

}
