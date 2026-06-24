package com.example.blogapp.Controllers;

import com.example.blogapp.Dto.PostDto;
import com.example.blogapp.ServiceInterfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    // Create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public PostDto createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        return postService.createPost(postDto, userId, categoryId);
    }

    // Update Post
    @PutMapping("/posts/{postId}")
    public PostDto updatePost(
            @RequestBody PostDto postDto,
            @PathVariable Integer postId) {

        return postService.updatePost(postDto, postId);
    }

    // Delete Post
    @DeleteMapping("/posts/{postId}")
    public String deletePost(
            @PathVariable Integer postId) {

        postService.deletePost(postId);
        return "Post deleted successfully";
    }

    // Get Single Post
    @GetMapping("/posts/{postId}")
    public PostDto getPostById(
            @PathVariable Integer postId) {

        return postService.getPostById(postId);
    }

    // Get All Posts
    @GetMapping("/posts")
    public List<PostDto> getAllPosts() {

        return postService.getAllPosts();
    }

    // Get Posts By User
    @GetMapping("/user/{userId}/posts")
    public List<PostDto> getPostsByUser(
            @PathVariable Integer userId) {

        return postService.getPostsByUser(userId);
    }

    // Get Posts By Category
    @GetMapping("/category/{categoryId}/posts")
    public List<PostDto> getPostsByCategory(
            @PathVariable Integer categoryId) {

        return postService.getPostsByCategory(categoryId);
    }

    // Search Posts
    @GetMapping("/posts/search/{keyword}")
    public List<PostDto> searchPosts(
            @PathVariable String keyword) {

        return postService.searchPosts(keyword);
    }
}
