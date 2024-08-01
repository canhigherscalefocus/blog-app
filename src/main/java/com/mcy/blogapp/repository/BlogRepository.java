package com.mcy.blogapp.repository;

import com.mcy.blogapp.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}