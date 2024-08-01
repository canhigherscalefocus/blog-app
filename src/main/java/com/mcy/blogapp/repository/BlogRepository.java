package com.mcy.blogapp.repository;

import com.mcy.blogapp.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b JOIN b.tagList t WHERE t.id = :tagId")
    List<Blog> findAllByTagId(@Param("tagId") Long tagId);
}