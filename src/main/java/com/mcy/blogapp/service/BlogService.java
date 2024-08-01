package com.mcy.blogapp.service;

import com.mcy.blogapp.model.Blog;
import com.mcy.blogapp.model.Tag;
import com.mcy.blogapp.repository.BlogRepository;
import com.mcy.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private final BlogRepository blogRepository;
    @Autowired
    private final TagRepository tagRepository;

    public BlogService(BlogRepository blogRepository, TagRepository tagRepository) {
        this.blogRepository = blogRepository;
        this.tagRepository = tagRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog blog) {
        if (blogRepository.existsById(id)) {
            blog.setId(id);
            return blogRepository.save(blog);
        }
        return null;
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public Blog addTagToBlog(Long blogId, Long tagId) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);
        Optional<Tag> tagOpt = tagRepository.findById(tagId);

        if (blogOpt.isPresent() && tagOpt.isPresent()) {
            Blog blog = blogOpt.get();
            Tag tag = tagOpt.get();
            blog.getTagList().add(tag);
            return blogRepository.save(blog);
        }
        return null;
    }

    public Blog removeTagFromBlog(Long blogId, Long tagId) {
        Optional<Blog> blogOpt = blogRepository.findById(blogId);
        Optional<Tag> tagOpt = tagRepository.findById(tagId);

        if (blogOpt.isPresent() && tagOpt.isPresent()) {
            Blog blog = blogOpt.get();
            Tag tag = tagOpt.get();
            blog.getTagList().remove(tag);
            return blogRepository.save(blog);
        }
        return null;
    }

    public List<Blog> getAllBlogsByTag(Long tagId) {
        return blogRepository.findAllByTagId(tagId);
    }
}
