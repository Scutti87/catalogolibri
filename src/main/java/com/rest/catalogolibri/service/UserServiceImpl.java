package com.rest.catalogolibri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rest.catalogolibri.repository.IUserRepository;
import com.rest.catalogolibri.model.User;

@Service
public class UserServiceImpl implements UserDetailsService{

	private final IUserRepository ur;
	
	@Autowired
	public UserServiceImpl(IUserRepository ur) {
		super();
		this.ur = ur;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Nessun User con username: " + username));
		
		return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
	}

	

}
