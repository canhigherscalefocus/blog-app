package com.mcy.blogapp.repository;

import com.mcy.blogapp.model.Blog;
import com.mcy.blogapp.model.Tag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void testCreateBlog() {
        Blog blog = new Blog();
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blog.");

        Tag tag1 = new Tag();
        tag1.setTitle("Java");

        Tag tag2 = new Tag();
        tag2.setTitle("Spring");

        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);

        blog.setTagList(tags);

        Blog savedBlog = blogRepository.save(blog);

        assertNotNull(savedBlog);
        assertEquals("My First Blog", savedBlog.getTitle());
        assertEquals("This is the content of the blog.", savedBlog.getText());
        assertEquals(2, savedBlog.getTagList().size());
    }

    @Test
    public void testFindBlogById() {
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        this.blogRepository.save(blog);

        Optional<Blog> foundBlog = blogRepository.findById(blog.getId());

        assertTrue(foundBlog.isPresent());
        assertEquals(blog.getTitle(), foundBlog.get().getTitle());
    }

    @Test
    public void testUpdateBlog() {
        Blog blog = new Blog();
        blog.setTitle("My First Blog");
        blog.setText("This is the content of the blogObj.");
        blogRepository.save(blog);

        Blog blogObj = blogRepository.findById(1L).orElse(null);
        assertNotNull(blogObj);

        blogObj.setTitle("Updated Blog Title");
        Blog updatedBlog = blogRepository.save(blogObj);

        assertEquals("Updated Blog Title", updatedBlog.getTitle());
    }

    @Test
    public void testDeleteBlog() {
        Blog blog = new Blog();
        blog.setTitle("Title to be deleted");
        blog.setText("Text to be deleted");
        blogRepository.save(blog);

        blogRepository.deleteById(blog.getId());

        Optional<Blog> deletedBlog = blogRepository.findById(blog.getId());

        assertFalse(deletedBlog.isPresent());
    }
}
