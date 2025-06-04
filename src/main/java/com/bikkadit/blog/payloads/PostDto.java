package com.bikkadit.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Integer postId;

	@NotEmpty
	@Size(min = 4, max = 100, message = "posttitle must be of min 4 chars and max of 100 chars")
	private String title;

	@NotEmpty
	@Size(min = 50, max = 1000, message = "postcontent must be of 50 chars and max of 1000 chars")
	private String content;
	@NotEmpty
	private String imageName;

	@NotEmpty
	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

}
