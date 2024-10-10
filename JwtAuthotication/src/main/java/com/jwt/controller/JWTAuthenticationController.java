package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.authuser.JwtHelpers;
import com.jwt.entity.JWTRequest;
import com.jwt.entity.JWTResponse;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JWTAuthenticationController {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtHelpers jwtHelpers;
	
	@PostMapping("/authenticate")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
		
		//authenticate for request we can method name is doAutheticate()
		this.doAuthenticate(request.getUsername(), request.getPassword());
		
		
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtHelpers.generateToken(userDetails);
		
		JWTResponse jwtResponse = JWTResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
		return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
		
		
	}
	
	
	private void doAuthenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authenticate);
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new BadCredentialsException("Creadential Invalid!!....");
		}
				
	}
	
	
	

}
