package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// logic to get user from database
		String password="$2a$10$HSxAne878IeaevbpVa8OAeqoUoF3UEnLcSKbB0VObF.4nXeVVFyKm";
		return new User("username",password,new ArrayList<>());
	}

}
