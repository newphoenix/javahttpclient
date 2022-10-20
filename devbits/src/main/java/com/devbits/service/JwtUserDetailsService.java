package com.devbits.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.devbits.config.JwtTokenUtil;
import com.devbits.dto.CredentialDto;
import com.devbits.dto.JwtResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtUserDetailsService {
	
	private UserService userService;
	private JwtTokenUtil jwtTokenUtil;
	private AuthenticationManager authenticationManager;
	
	public JwtResponse getToken(CredentialDto cridentialDto) {
		
		authenticate(cridentialDto.getUsername(), cridentialDto.getPassword());
		
		final UserDetails userDetails = userService.loadUserByUsername(cridentialDto.getUsername());
		
		Set<String> authorities = userDetails.getAuthorities().stream().map(e -> e.getAuthority())
				.collect(Collectors.toSet());
		
		final String token = jwtTokenUtil.generateToken(userDetails, authorities);
		return new JwtResponse(token);
	}

	private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException | BadCredentialsException e) {
			throw e;
		}
	}

}
