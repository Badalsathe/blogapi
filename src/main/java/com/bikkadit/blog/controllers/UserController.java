package com.bikkadit.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.blog.payloads.ApiResponse;
import com.bikkadit.blog.payloads.UserDto;
import com.bikkadit.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	// POST- Create user

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		logger.info("Initiating the request to create user");
		UserDto createUserDto = this.userService.createUser(userDto);
		logger.info("Compliting the request to create user");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	// PUT- Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {

		logger.info("Initiating the request to update user with userId: {} ", userId);
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		logger.info("Compliting the request to update user with userId: {} ", userId);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

	// DELETE- Delete user

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {

		logger.info("Initiating the request to Delete user with userId: {} ", userId);
		this.userService.deleteUser(userId);
		logger.info("Compliting the request to Delete user with userId: {} ", userId);
		return new ResponseEntity<>(new ApiResponse("user delete successfully", true), HttpStatus.OK);

	}

	// GET - user get

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {

		logger.info("Initiating the request to get all user");
		List<UserDto> allUsers = this.userService.getAllUsers();
		logger.info("Compliting the request to get all user");
		return new ResponseEntity<>(allUsers, HttpStatus.OK);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {

		logger.info("Initiating the request get singleUser to user by userid: {}", userId);
		UserDto userById = this.userService.getUserById(userId);
		logger.info("Compliting the request  get singleUser to user by userid: {}", userId);
		return new ResponseEntity<>(userById, HttpStatus.OK);

	}

}
