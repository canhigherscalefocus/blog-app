package com.mcy.blogapp.controller;

import com.mcy.blogapp.model.Tag;
import com.mcy.blogapp.service.TagService;
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
@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    private Tag tag;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tag = new Tag();
        tag.setId(1L);
        tag.setTitle("Test Tag");
    }

    @Test
    void testGetAllTags() throws Exception {
        when(tagService.getAllTags()).thenReturn(Collections.singletonList(tag));

        mockMvc.perform(get("/tags/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(tag.getTitle()));
    }

    @Test
    void testGetTag() throws Exception {
        when(tagService.getTagById(1L)).thenReturn(Optional.of(tag));

        mockMvc.perform(get("/tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(tag.getTitle()));
    }

    @Test
    void testCreateTag() throws Exception {
        when(tagService.createTag(any(Tag.class))).thenReturn(tag);

        mockMvc.perform(post("/tags/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Tag\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(tag.getTitle()));
    }

    @Test
    void testUpdateTag() throws Exception {
        when(tagService.updateTag(anyLong(), any(Tag.class))).thenReturn(tag);

        mockMvc.perform(put("/tags/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Tag\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(tag.getTitle()));
    }

    @Test
    void testDeleteTag() throws Exception {
        mockMvc.perform(delete("/tags/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
