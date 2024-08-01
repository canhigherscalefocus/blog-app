package com.mcy.blogapp.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class BlogTest {

    @Test
    public void testBlogFields() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blog.");

        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setTitle("Java");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setTitle("Spring");

        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);

        blog.setTagList(tags);

        assertEquals(1L, blog.getId());
        assertEquals("My First Blog", blog.getTitle());
        assertEquals("This is the content of the blog.", blog.getText());
        assertEquals(2, blog.getTagList().size());
    }

    @Test
    public void testAddTag() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blog.");

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTitle("Java");

        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        blog.setTagList(tags);

        assertTrue(blog.getTagList().contains(tag));
    }

    @Test
    public void testRemoveTag() {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blog.");

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTitle("Java");

        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        blog.setTagList(tags);
        blog.getTagList().remove(tag);

        assertFalse(blog.getTagList().contains(tag));
    }
}
