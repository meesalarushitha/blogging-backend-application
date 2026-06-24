package com.example.blogapp.Controllers;

import com.example.blogapp.Dto.CommentDto;
import com.example.blogapp.ServiceInterfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create Comment
    @PostMapping("/post/{postId}/user/{userId}/comments")
    public CommentDto createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId) {

        return commentService.createComment(
                commentDto,
                postId,
                userId
        );
    }

    // Update Comment
    @PutMapping("/comments/{commentId}")
    public CommentDto updateComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer commentId) {

        return commentService.updateComment(
                commentDto,
                commentId
        );
    }

    // Delete Comment
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(
            @PathVariable Integer commentId) {

        commentService.deleteComment(commentId);

        return "Comment deleted successfully";
    }

    // Get Comments By Post
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPost(
            @PathVariable Integer postId) {

        return commentService.getCommentsByPost(postId);
    }
}
