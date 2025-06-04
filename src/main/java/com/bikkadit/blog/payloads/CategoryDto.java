package com.bikkadit.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4 , message = "Minimum size of category title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10 , message = "Minimum size of category desc is 10")
	private String categoryDescription;
	
	
	
	

}
