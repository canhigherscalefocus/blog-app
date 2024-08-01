package com.mcy.blogapp.controller;

import com.mcy.blogapp.model.Blog;
import com.mcy.blogapp.model.Tag;
import com.mcy.blogapp.model.dto.AddTagDto;
import com.mcy.blogapp.service.BlogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Optional<Blog> getBlog(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping("/")
    public Blog createBlog(@RequestBody Blog blog) {
        return blogService.createBlog(blog);
    }

    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        return blogService.updateBlog(id, blog);
    }

    //todo: may be put to body to increase malicious attack?
    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }

    //todo: may be change the endpoint to tag controller?
    @GetMapping("/tags/{tagId}")
    public List<Blog> getAllBlogsByTag(@PathVariable Long tagId) {
        return blogService.getAllBlogsByTag(tagId);
    }

    @PostMapping("/{blogId}/tags")
    public Blog addTagToBlog(@PathVariable Long blogId, @RequestBody AddTagDto addTagDto) {
        return blogService.addTagToBlog(blogId, addTagDto.getTagId());
    }

    @DeleteMapping("/{blogId}/tags")
    public Blog removeTagFromBlog(@PathVariable Long blogId, @RequestBody AddTagDto addTagDto) {
        return blogService.removeTagFromBlog(blogId, addTagDto.getTagId());
    }
}
