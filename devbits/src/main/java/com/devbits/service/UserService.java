package com.devbits.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devbits.dto.NewUserDto;
import com.devbits.dto.UserCV;
import com.devbits.dto.UserDto;
import com.devbits.entity.User;
import com.devbits.exceptions.UserNotFoundException;
import com.devbits.repository.AuthorityRepository;
import com.devbits.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetails = userRepository.findByUsername(username)
				.map(user -> new org.springframework.security.core.userdetails.User(//
						user.getUsername(), //
						user.getPassword(), //
						user.isEnabled(), true, true, true,
						authorityRepository.getAuthoritiesByUserName(user.getUsername()).stream()
								.map(a -> new SimpleGrantedAuthority(a)).toList()

				)).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return userDetails;
	}

	public List<UserDto> getUsers() {
		return userRepository.findAll().stream().map(user -> UserDto.builder().email(user.getEmail())
				.username(user.getUsername()).enabled(user.isEnabled()).build()).toList();

	}

	public UserDto getUserById(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

		return UserDto.builder().username(user.getUsername()).email(user.getEmail()).enabled(user.isEnabled()).build();
	}

	public UserDto createUser(NewUserDto newUser) {

		User user = User.builder().email(newUser.getEmail()).enabled(newUser.getEnabled())
				.username(newUser.getUsername())
				.password((new BCryptPasswordEncoder(BCryptVersion.$2Y, 12)).encode(newUser.getPassword())).build();

		User pUser = userRepository.save(user);

		return UserDto.builder().id(pUser.getId()).username(pUser.getUsername()).email(pUser.getEmail())
				.enabled(pUser.isEnabled()).build();
	}

	public boolean saveCV(UserCV cv) {

		boolean result = true;

		try {

			Path path = Paths.get(cv.getCv().getName());
			Files.write(path, cv.getCv().getBytes());

		} catch (Exception e) {
			System.out.println(e);
			result = false;
		}

		return result;
	}

}
