package com.mcy.blogapp.service;

import com.mcy.blogapp.model.Tag;
import com.mcy.blogapp.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(1L);
        tag.setTitle("Java");
    }

    @Test
    void testGetAllTags() {
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        List<Tag> tags = tagService.getAllTags();
        assertNotNull(tags);
        assertEquals(1, tags.size());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void testGetTagById() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        Optional<Tag> foundTag = tagService.getTagById(1L);
        assertTrue(foundTag.isPresent());
        assertEquals(tag.getTitle(), foundTag.get().getTitle());
        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTagByTitle() {
        when(tagRepository.findByTitle("Java")).thenReturn(Optional.of(tag));
        Optional<Tag> foundTag = tagService.getTagByTitle("Java");
        assertTrue(foundTag.isPresent());
        assertEquals(tag.getTitle(), foundTag.get().getTitle());
        verify(tagRepository, times(1)).findByTitle("Java");
    }

    @Test
    void testCreateTag() {
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        Tag savedTag = tagService.createTag(tag);
        assertNotNull(savedTag);
        assertEquals(tag.getTitle(), savedTag.getTitle());
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testUpdateTag() {
        when(tagRepository.existsById(1L)).thenReturn(true);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
        Tag updatedTag = tagService.updateTag(1L, tag);
        assertNotNull(updatedTag);
        assertEquals(tag.getTitle(), updatedTag.getTitle());
        verify(tagRepository, times(1)).existsById(1L);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testUpdateTag_NotFound() {
        when(tagRepository.existsById(1L)).thenReturn(false);
        Tag updatedTag = tagService.updateTag(1L, tag);
        assertNull(updatedTag);
        verify(tagRepository, times(1)).existsById(1L);
        verify(tagRepository, times(0)).save(tag);
    }

    @Test
    void testDeleteTag() {
        doNothing().when(tagRepository).deleteById(1L);
        tagService.deleteTag(1L);
        verify(tagRepository, times(1)).deleteById(1L);
    }
}
