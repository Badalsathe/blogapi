package com.bikkadit.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "username must be min of 4 characters !!")
	private String name;

	@Email(message = "email address is not valid !!")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "password must be min of 3 chars and max of 10 chars!!")
	private String pass;

	@NotEmpty
	private String about;

	public static Object size() {
		// TODO Auto-generated method stub
		return null;
	}

}
