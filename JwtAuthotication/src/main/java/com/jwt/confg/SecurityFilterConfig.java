package com.jwt.confg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.authuser.JwtAutenticationEntryPonit;
import com.jwt.authuser.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

//@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class SecurityFilterConfig {
	@Autowired
	private JwtAutenticationEntryPonit point;
	
	private JwtAuthenticationFilter filter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
	{
		return security.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/authenticate", "/home/getAll").permitAll()
						.anyRequest().authenticated())
						.exceptionHandling(ex-> ex.authenticationEntryPoint(point))
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
						.build();
		
	}

}
