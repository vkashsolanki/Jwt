package com.jwt.authuser;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtHelpers jwtHelpers;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("Authorization");
		log.info("Header : {} ", requestHeader);
		
		
		String username = null;
		String token = null;
		
		//Bearer will be generate with token and beyond the token will used
		if(requestHeader != null && requestHeader.startsWith("Bearer")) {
		token = requestHeader.substring(7);
		
		try {
			 username = this.jwtHelpers.getUsernameFromToken(token);
			
		} catch (IllegalArgumentException e) {
			logger.info("Illegal Argument while fetching the username !! ");
			e.printStackTrace();
		} catch(ExpiredJwtException e) {
			logger.info("Given Jwt token is expired !!");
			e.printStackTrace();
		}catch(MalformedJwtException e) {
			logger.info("Some Changed has done in Token !! ");
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}else
	{
		logger.info("Invalid Header Value !! ");
	}
		
	if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		// fetch user detail from username
		UserDetails usersDetail = this.userDetailsService.loadUserByUsername(username);
		boolean validateToken = this.jwtHelpers.validateToken(token, usersDetail);
		if(validateToken) {
			//set the authentication
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usersDetail, null, usersDetail.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}else
		{
			logger.info("Validation Fail !!...");
		}
	}
		filterChain.doFilter(request, response);

}
}














