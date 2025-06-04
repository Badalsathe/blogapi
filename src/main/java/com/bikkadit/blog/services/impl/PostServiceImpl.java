package com.bikkadit.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bikkadit.blog.entities.Category;
import com.bikkadit.blog.entities.Post;
import com.bikkadit.blog.entities.User;
import com.bikkadit.blog.exceptions.ResourceNotFoundException;
import com.bikkadit.blog.payloads.PostDto;
import com.bikkadit.blog.payloads.PostResponse;
import com.bikkadit.blog.repositories.CategoryRepo;
import com.bikkadit.blog.repositories.PostRepo;
import com.bikkadit.blog.repositories.UserRepository;
import com.bikkadit.blog.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		log.info("Initiating the request to create dao call for post by categoryId,userId: {}", categoryId, userId);
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		log.info("Compliting the request to create dao call for post by categoryId,userId: {}", categoryId, userId);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	// update
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		log.info("Initiating the request to update a dao call for post by postId: {} ", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post-id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedpost = this.postRepo.save(post);
		log.info("Compliting the request to update a dao call for post by postId: {} ", postId);
		return this.modelMapper.map(updatedpost, PostDto.class);
	}

	// delete

	@Override
	public void deletePost(Integer postId) {

		log.info("Initiating the request to delete a dao call for post by postId: {} ", postId);
		Post byId = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post-id", postId));
		this.postRepo.delete(byId);
		log.info("Compliting the request to delete a dao call for post by postId: {} ", postId);
	}

	// get all posts

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		log.info("Initiating the request to get a dao call for all posts ");

		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		PageRequest p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();

		List<PostDto> postdtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postdtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getNumberOfElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		log.info("Compliting the request to get a dao call for all posts ");
		return postResponse;
	}

	// get single post

	@Override
	public PostDto getPostById(Integer postId) {

		log.info("Initiating the request to get a dao call for post by postid: {} ", postId);
		Post Post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post-id", postId));
		log.info("Compliting the request to get a dao call for post by postid: {} ", postId);
		return this.modelMapper.map(Post, PostDto.class);
	}

	// get all posts by category

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		log.info("Initiating the request to get dao call for post by categoryId: {}", categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Compliting the request to get dao call for post by categoryId: {}", categoryId);
		return postDtos;
	}

	// get all posts by user

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		log.info("Initiating the request to get dao call for post by userId: {}", userId);
		Category user = this.categoryRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(posts, PostDto.class))
				.collect(Collectors.toList());
		log.info("Compliting the request to get dao call for post by userId: {}", userId);
		return postDtos;

	}

	// search posts

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		log.info("Initiating the request to search dao call for posts");
		List<Post> posts = this.postRepo.findByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		log.info("Compliting the request to search dao call for posts");
		return postDtos;
	}

}
