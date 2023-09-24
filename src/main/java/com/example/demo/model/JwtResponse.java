package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public JwtResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
