package com.example.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.services.UserService;
import com.example.demo.utility.JWTUtility;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtility utility;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String username = null;
		String header = request.getHeader("Authorization");
		String token = null;
		if (header != null && header.startsWith("Bearer ")) {
			token = header.split(" ")[1];
			try {
				username = this.utility.getUsernameFromToken(token);

			} catch (Exception e) {
				e.printStackTrace();
			}

			UserDetails userDetails = this.userService.loadUserByUsername(username);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				
			} else {
				System.out.print("sfjij");
			}
		}

		filterChain.doFilter(request, response);
	}

}
