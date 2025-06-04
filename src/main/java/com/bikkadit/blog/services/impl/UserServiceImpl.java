package com.bikkadit.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.blog.entities.User;
import com.bikkadit.blog.exceptions.ResourceNotFoundException;
import com.bikkadit.blog.payloads.UserDto;
import com.bikkadit.blog.repositories.UserRepository;
import com.bikkadit.blog.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {

		log.info("Initiating the request to dao call for create user");
		User user = this.dtotoUser(userDto);
		User savedUser = this.userRepository.save(user);
		log.info("Compliting the request to dao call for create user");
		return this.usertoDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		log.info("Initiating the request to update a dao call for User by userId");
		User user2 = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));

		user2.setName(userDto.getName());
		user2.setEmail(userDto.getEmail());
		user2.setPass(userDto.getPass());
		user2.setAbout(userDto.getAbout());

		User updatedUser = this.userRepository.save(user2);

		UserDto userDto1 = this.usertoDto(updatedUser);
		log.info("Compliting the request to update a dao call for User by userId");
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		log.info("Initiating the request to get User a dao call for User by userid: {}", userId);
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		 log.info("Compliting the request to get User a dao call for User by userid: {}", userId);
		return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		log.info("Initiating the request to get all users");
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.usertoDto(user)).collect(Collectors.toList());
		 log.info("Compliting the request to get all users");
		return userDtos;
	}

	@Override
	public void deleteUser(Integer UserId) {
		
		log.info("Initiating the request to Delete user by userid: {}", UserId);
		User user = this.userRepository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", UserId));
		 log.info("Compliting the request to Delete user by userid: {}", UserId);

		this.userRepository.delete(user);

	}

	public User dtotoUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		return user;

	}

	public UserDto usertoDto(User user) {

		UserDto userdto = this.modelMapper.map(user, UserDto.class);

		return userdto;

	}

}
