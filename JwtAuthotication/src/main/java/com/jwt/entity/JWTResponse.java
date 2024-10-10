package com.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder //when we repeaird response that time use @Builder
public class JWTResponse {
	private String username;
	private String jwtToken;

}
