package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.JwtRequest;
import com.example.demo.model.JwtResponse;
import com.example.demo.services.UserService;
import com.example.demo.utility.JWTUtility;

@RestController
public class HomeController {

	@Autowired
	private JWTUtility utility;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@GetMapping("/home")
	public String homeController() {
		return "welcome to my site";
	}
	
	@PostMapping("/register")
	ResponseEntity<?> createUser(@RequestBody JwtRequest request)
	{
		
		//encrypt password with bcrypt
		request.setPassword(this.encoder.encode(request.getPassword()));
		
		//save to db
		
		return new ResponseEntity<>(request,HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody JwtRequest request) throws Exception {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {

//			return new ResponseEntity(HttpStatus.BAD_REQUEST);
			System.out.print("sdfhjsdfdhj");
			throw new Exception("INVALID_CREDENTIALS",e);
		}

		UserDetails userDetails=userService.loadUserByUsername(request.getUsername());
		String token=utility.generateJwtToken(userDetails);
		System.out.print("sdfhjsdfdhj");
		return ResponseEntity.ok(new JwtResponse(token));
	}
}
