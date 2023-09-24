package com.example.demo.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.services.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
 
	@Autowired
	UserService userService;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired
	UnauthorizedException exception;

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/register").permitAll()
		.antMatchers("/home").permitAll()
		.anyRequest().authenticated().and()
		.cors().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.exceptionHandling().authenticationEntryPoint(exception).and()
		.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
	}



	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
}
