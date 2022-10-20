package com.devbits.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devbits.dto.CredentialDto;
import com.devbits.dto.JwtResponse;
import com.devbits.service.JwtUserDetailsService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {
	
	private JwtUserDetailsService jwtUserDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody CredentialDto cridentialDto) {
		return ResponseEntity.ok(jwtUserDetailsService.getToken(cridentialDto));
	}
}
