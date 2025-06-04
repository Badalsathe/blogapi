package com.bikkadit.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.blog.config.AppConstants;
import com.bikkadit.blog.payloads.ApiResponse;
import com.bikkadit.blog.payloads.PostDto;
import com.bikkadit.blog.payloads.PostResponse;
import com.bikkadit.blog.services.FileService;
import com.bikkadit.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		logger.info("Initiating the Request to create post by userId,categoryId: {}", userId, categoryId);
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		logger.info("Compliting the Request to create post by userId,categoryId: {}", userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

		logger.info("Initiating the request to get post by userId: {}", userId);
		List<PostDto> posts = this.postService.getPostByUser(userId);
		logger.info("Compliting the request to get post by userId: {}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get By category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {

		logger.info("Initiating the request to get post by categoryId: {}", categoryId);
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		logger.info("Compliting the request to get post by categoryId: {}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get all posts

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		logger.info("Initiating the request to get all posts");
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		logger.info("Compliting the request to get all posts");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

	}

	// get post details by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		logger.info("Initiating the request to get all post by postId: {} ", postId);
		PostDto postDtos = this.postService.getPostById(postId);
		logger.info("Compliting the request to get all post by postId: {} ", postId);
		return new ResponseEntity<PostDto>(postDtos, HttpStatus.OK);

	}

	// delete post

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {

		logger.info("Initiating the request to delete post by postId: {} ", postId);
		this.postService.deletePost(postId);
		logger.info("Compliting the request to delete post by postId: {} ", postId);
		return new ApiResponse("post is successfully deleted !!", true);

	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		logger.info("Initiating the request to update post by postId: {} ", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("Compliting the request to update post by postId: {} ", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	// search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {

		logger.info("Initiating the request to search posts");
		List<PostDto> result = this.postService.searchPosts(keywords);
		logger.info("Compliting the request to search posts");
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

	// post Image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		logger.info("Initiating the request to upload images post by postid: {} ", postId);
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("Compliting the request to upload images post by postid: {} ", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}
	
	//method to serve file
	@GetMapping(value = "/post/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response)throws IOException {
		
		InputStream resource = this.fileService.getResource(imageName, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
}
