package com.example.blogapp.ServiceInterfaces;

import com.example.blogapp.Dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    CommentDto updateComment(CommentDto commentDto, Integer commentId);

    void deleteComment(Integer commentId);

    List<CommentDto> getCommentsByPost(Integer postId);
}
