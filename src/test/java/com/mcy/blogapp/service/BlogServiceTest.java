package com.mcy.blogapp.service;

import com.mcy.blogapp.model.Blog;
import com.mcy.blogapp.model.Tag;
import com.mcy.blogapp.repository.BlogRepository;
import com.mcy.blogapp.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private BlogService blogService;

    private Blog blog;
    private Tag tag;

    @BeforeEach
    void setUp() {
        blog = new Blog();
        blog.setId(1L);
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blog.");
        blog.setTagList(new HashSet<>());

        tag = new Tag();
        tag.setId(1L);
        tag.setTitle("Java");
    }

    @Test
    void testGetAllBlogs() {
        when(blogRepository.findAll()).thenReturn(List.of(blog));
        List<Blog> blogs = blogService.getAllBlogs();
        assertNotNull(blogs);
        assertEquals(1, blogs.size());
        verify(blogRepository, times(1)).findAll();
    }

    @Test
    void testGetBlogById() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        Optional<Blog> foundBlog = blogService.getBlogById(1L);
        assertTrue(foundBlog.isPresent());
        assertEquals("My First Blog", foundBlog.get().getTitle());
        verify(blogRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBlog() {
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Blog savedBlog = blogService.createBlog(blog);
        assertNotNull(savedBlog);
        assertEquals(blog.getTitle(), savedBlog.getTitle());
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testUpdateBlog() {
        when(blogRepository.existsById(1L)).thenReturn(true);
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Blog updatedBlog = blogService.updateBlog(1L, blog);
        assertNotNull(updatedBlog);
        assertEquals(blog.getTitle(), updatedBlog.getTitle());
        verify(blogRepository, times(1)).existsById(1L);
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testUpdateBlog_NotFound() {
        when(blogRepository.existsById(1L)).thenReturn(false);
        Blog updatedBlog = blogService.updateBlog(1L, blog);
        assertNull(updatedBlog);
        verify(blogRepository, times(1)).existsById(1L);
        verify(blogRepository, times(0)).save(blog);
    }

    @Test
    void testDeleteBlog() {
        doNothing().when(blogRepository).deleteById(1L);
        blogService.deleteBlog(1L);
        verify(blogRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddTagToBlog() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Blog updatedBlog = blogService.addTagToBlog(1L, 1L);
        assertNotNull(updatedBlog);
        assertTrue(updatedBlog.getTagList().contains(tag));
        verify(blogRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testAddTagToBlog_NotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        Blog updatedBlog = blogService.addTagToBlog(1L, 1L);
        assertNull(updatedBlog);
        verify(blogRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(blogRepository, times(0)).save(blog);
    }

    @Test
    void testRemoveTagFromBlog() {
        blog.getTagList().add(tag);
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Blog updatedBlog = blogService.removeTagFromBlog(1L, 1L);
        assertNotNull(updatedBlog);
        assertFalse(updatedBlog.getTagList().contains(tag));
        verify(blogRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testRemoveTagFromBlogIsPresent() {
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog));
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());
        Blog updatedBlog = blogService.removeTagFromBlog(1L, 1L);
        assertNull(updatedBlog);
        verify(blogRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveTagFromBlog_NotFound() {
        when(blogRepository.findById(1L)).thenReturn(Optional.empty());
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        Blog updatedBlog = blogService.removeTagFromBlog(1L, 1L);
        assertNull(updatedBlog);
        verify(blogRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).findById(1L);
        verify(blogRepository, times(0)).save(blog);
    }
}
