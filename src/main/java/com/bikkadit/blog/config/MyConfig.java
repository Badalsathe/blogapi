package com.bikkadit.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bikkadit.blog.services.CategoryService;
import com.bikkadit.blog.services.impl.CategoryServiceImpl;

@Configuration
public class MyConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
		
	}
	
	 


}
