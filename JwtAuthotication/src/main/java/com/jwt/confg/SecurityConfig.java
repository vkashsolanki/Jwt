package com.jwt.confg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

	// for pass encrypted here
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Create for user here as much as
	@Bean
	public UserDetailsService userDetailsService() {
		// single user are creating for admin and password encoding here 
		UserDetails adminUser = User.builder()
		.username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
		// single user are creating for user and password encoding here 
		UserDetails normalUser = User.builder().username("solanki").password(passwordEncoder().encode("solanki")).roles("USER").build();

		// Hence we have to save both user with constructor of
		// InMemoryUserDetailsManager
		return new InMemoryUserDetailsManager(adminUser, normalUser);

	}

	// AuthotenticationManager will be present in configuration type of
	// AuthenticationConfiguration which has local variable

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();

	}

}
