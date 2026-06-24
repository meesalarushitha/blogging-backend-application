package com.example.blogapp.ServiceImpl;


import com.example.blogapp.Dto.CommentDto;
import com.example.blogapp.Entity.Comment;
import com.example.blogapp.Entity.Post;
import com.example.blogapp.Entity.User;
import com.example.blogapp.Exception.ResourceNotFoundException;
import com.example.blogapp.Repository.CommentRepository;
import com.example.blogapp.Repository.PostRepository;
import com.example.blogapp.Repository.UserRepository;
import com.example.blogapp.ServiceInterfaces.CommentService;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "id", userId));
        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment", "id", commentId));

        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment", "id", commentId));

        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post", "id", postId));

        return post.getComments()
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }
}
