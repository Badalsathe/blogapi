package com.bikkadit.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bikkadit.blog.entities.Category;
import com.bikkadit.blog.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(Category user);
	
	List<Post> findByCategory(Category category);
	
	@Query(" select p from Post p where p.title like :key")
	List<Post> findByTitle(@Param ("key") String title);

}
