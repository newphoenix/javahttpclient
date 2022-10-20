package com.devbits.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devbits.dto.NewUserDto;
import com.devbits.dto.UserCV;
import com.devbits.dto.UserDto;
import com.devbits.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getUsers(){
		return ResponseEntity.ok(userService.getUsers());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<UserDto> getUsersById(@PathVariable Integer id){
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@ModelAttribute NewUserDto newUserDto){
		return ResponseEntity.ok(userService.createUser(newUserDto));
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Boolean> uploadCV(@ModelAttribute UserCV cv){
		return ResponseEntity.ok(userService.saveCV(cv));
	}
}
