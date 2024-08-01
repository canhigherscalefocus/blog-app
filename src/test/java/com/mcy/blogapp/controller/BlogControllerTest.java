package com.mcy.blogapp.controller;

import com.mcy.blogapp.model.Blog;
import com.mcy.blogapp.model.dto.AddTagDto;
import com.mcy.blogapp.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    private Blog blog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
    }

    @Test
    void testGetAllBlogs() throws Exception {
        when(blogService.getAllBlogs()).thenReturn(Collections.singletonList(blog));

        mockMvc.perform(get("/blogs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(blog.getTitle()));
    }

    @Test
    void testGetBlog() throws Exception {
        when(blogService.getBlogById(1L)).thenReturn(Optional.of(blog));

        mockMvc.perform(get("/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(blog.getTitle()));
    }

    @Test
    void testCreateBlog() throws Exception {
        when(blogService.createBlog(any(Blog.class))).thenReturn(blog);

        mockMvc.perform(post("/blogs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Blog\", \"text\": \"This is a test blog.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(blog.getTitle()));
    }

    @Test
    void testUpdateBlog() throws Exception {
        when(blogService.updateBlog(anyLong(), any(Blog.class))).thenReturn(blog);

        mockMvc.perform(put("/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Blog\", \"text\": \"This is an updated test blog.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(blog.getTitle()));
    }

    @Test
    void testDeleteBlog() throws Exception {
        mockMvc.perform(delete("/blogs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddTagToBlog() throws Exception {
        when(blogService.addTagToBlog(anyLong(), anyLong())).thenReturn(blog);

        AddTagDto addTagDto = new AddTagDto();
        addTagDto.setTagId(1L);

        mockMvc.perform(post("/blogs/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(blog.getTitle()));
    }

    @Test
    void testRemoveTagFromBlog() throws Exception {
        when(blogService.removeTagFromBlog(anyLong(), anyLong())).thenReturn(blog);

        AddTagDto addTagDto = new AddTagDto();
        addTagDto.setTagId(1L);

        mockMvc.perform(delete("/blogs/1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(blog.getTitle()));
    }

//    @Test
//    void testGetAllBlogsByTag() throws Exception {
//        when(blogService.getAllBlogsByTag(tagId)).thenReturn(Collections.singletonList(blog));
//
//        mockMvc.perform(get("/blogs/tags/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].title").value(blog.getTitle()));
//    }
}
