package com.bikkadit.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.blog.entities.User;

public interface UserRepository  extends JpaRepository<User, Integer>{
	
	

}
