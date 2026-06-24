package com.example.blogapp.ServiceImpl;


import com.example.blogapp.Dto.PostDto;
import com.example.blogapp.Entity.Category;
import com.example.blogapp.Entity.Post;
import com.example.blogapp.Entity.User;
import com.example.blogapp.Exception.ResourceNotFoundException;
import com.example.blogapp.Repository.CategoryRepository;
import com.example.blogapp.Repository.PostRepository;
import com.example.blogapp.Repository.UserRepository;
import com.example.blogapp.ServiceInterfaces.PostService;
import org.apache.tomcat.util.descriptor.web.ResourceBase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);

        post.setUser(user);
        post.setCategory(category);
        post.setPostDate(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","PostId",postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
       return postRepository.findAll()
               .stream()
               .map(post -> modelMapper.map(post,PostDto.class))
               .toList();
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
          Category category = categoryRepository.findById(categoryId)
                  .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
          return postRepository.findByCategory(category, Pageable.unpaged())
                  .stream()
                  .map(post -> modelMapper.map(post,PostDto.class))
                  .toList();
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",userId));
        return postRepository.findByUser(user, Pageable.unpaged())
                .stream()
                .map(post -> modelMapper.map(post,PostDto.class))
                .toList();

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
     return  postRepository.findByTitleContaining(keyword)
             .stream()
             .map(post -> modelMapper.map(post,PostDto.class))
             .toList();
    }
}
