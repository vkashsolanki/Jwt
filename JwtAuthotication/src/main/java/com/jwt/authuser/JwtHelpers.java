package com.jwt.authuser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelpers {
	
	// 5 hour 60 minutes and 6 seconds but it will take millisecond so  you can multiple with 1000
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	//it should be long but not short 
	private String secret ="afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
	
	
	//Retrieve Username form jwt token
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(secret, Claims::getSubject);
		
	}
	
	
	//Retrieve Expiration Date form jwt token 
	private Date getExpirationDateFromToken(String token) {
		// TODO Auto-generated method stub
		return getClaimsFromToken(token, Claims::getExpiration);
	}
	
	
	
	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver ) {
		final Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);
	}
	
	
	// For retrieve any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	
	
	//Check if the user token has expired
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
		
	}


	// Generate Token For User
	public String generateToken(UserDetails userDetails) {
		
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
		
	}
	
	
	
	
	//while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
	
	private String doGenerateToken(Map<String , Object> claims, String subject)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // converting millisecond with 1000
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	
		
	}
	
	
	// validate token
	public boolean validateToken(String token, UserDetails details) {
		final String username = getUsernameFromToken(token);
		return (username.equals(details.getUsername()) && !isTokenExpired(token));
		
	}
	
}

