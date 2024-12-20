package com.rest.catalogolibri.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rest.catalogolibri.security.JwtAuthenticationFilter;
import com.rest.catalogolibri.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jaf;
	private final UserServiceImpl us;

	@Autowired
	public SecurityConfig(UserServiceImpl us, JwtAuthenticationFilter jaf) {
		this.us = us;
		this.jaf = jaf;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .authorizeRequests()
	            .requestMatchers("/login.html", "/home", "/ruolo/**", "/autentica", "/error", "/css/**", "/favicon.ico").permitAll()
	            .requestMatchers("/api/libro/**").authenticated()
	            .anyRequest().authenticated()
	        .and()
	        .exceptionHandling()
	            .authenticationEntryPoint((request, response, authException) -> {
	                System.out.println("Accesso negato: " + authException.getMessage());
	                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Non autenticato");
	            })
	        .and()
	        .addFilterBefore(jaf, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}





	@Bean
	public AuthenticationManager authManager(HttpSecurity https, PasswordEncoder pe) throws Exception {

		return https.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(us).passwordEncoder(pe)
				.and().build();
	}

	@Bean
	public PasswordEncoder passwordEndcoder() {
		return new BCryptPasswordEncoder();
	}
}
