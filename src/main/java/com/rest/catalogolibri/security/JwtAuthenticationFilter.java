package com.rest.catalogolibri.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.rest.catalogolibri.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil ju;
	private final UserDetailsService uds;

	public JwtAuthenticationFilter(JwtUtil ju, UserDetailsService uds) {
		this.ju = ju;
		this.uds = uds;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    final String authHeader = request.getHeader("Authorization");

	    // Log di controllo sull'intestazione Authorization
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        System.out.println("Authorization header mancante o non valido.");
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String jwt = authHeader.substring(7);
	    final String username = ju.extractUsername(jwt);

	    System.out.println("Token estratto: " + jwt);
	    System.out.println("Username estratto: " + username);

	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails us = uds.loadUserByUsername(username);

	        System.out.println("UserDetails caricato: " + us);

	        if (ju.isTokenValid(jwt, us.getUsername())) {
	            System.out.println("Token valido per username: " + username);

	            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                    us, null, us.getAuthorities());
	            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            SecurityContextHolder.getContext().setAuthentication(authToken);
	            System.out.println("Authentication set for: " + username);
	        } else {
	            System.out.println("Token non valido per username: " + username);
	        }
	    } else {
	        System.out.println("Username nullo o già autenticato.");
	    }

	    filterChain.doFilter(request, response);
	}


}
