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
import com.bikkadit.blog.payloads.CategoryDto;
import com.bikkadit.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	// create

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		logger.info("Initiating the request to create category");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		logger.info("Compliting the request to create category");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {

		logger.info("Initiating the request to update Category by catId: {} ", catId);
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
		logger.info("Compliting the request to update Category by catId: {}", catId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.CREATED);

	}

	// delete

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {

		logger.info("Initiating the request to Delete Category by catId: {} ", catId);
		this.categoryService.deleteCategory(catId);
		logger.info("Compliting the request to Delete Category by catId: {}", catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully.!!", true),
				HttpStatus.OK);

	}
	// get

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {

		logger.info("Initiating the request to get Category by catId: {}", catId);
		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		logger.info("Compliting the request to get Category by catId: {}", catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}

	// getAll



	

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories() {

		logger.info("Initiating the request to get all List of Categories");
		List<CategoryDto> categories = this.categoryService.getCatogories();
		logger.info("Compliting the request to get all List of  Categories");
		return ResponseEntity.ok(categories);
	}

}
