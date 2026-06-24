package com.example.blogapp.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int postId;
    private String title;
    private String content;
    private String imageName;
    private LocalDateTime postDate;
    private CategoryDto category;
    private UserDto user;
    private List<CommentDto> comments = new ArrayList<>();
}
