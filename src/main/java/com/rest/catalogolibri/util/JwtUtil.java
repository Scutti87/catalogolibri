package com.rest.catalogolibri.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "secret";

	public String generateToken(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, String username) {
	    String extractedUsername = extractUsername(token);
	    boolean isExpired = isTokenExpired(token);

	    System.out.println("Username estratto dal token: " + extractedUsername);
	    System.out.println("Token scaduto: " + isExpired);

	    return extractedUsername.equals(username) && !isExpired;
	}

	
	private boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	private Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
}
