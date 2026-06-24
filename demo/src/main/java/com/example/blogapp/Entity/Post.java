package com.example.blogapp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int postId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,length=10000)
    private String content;
    private String imageName;
    private LocalDateTime postDate;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(cascade =CascadeType.ALL,mappedBy = "post",orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();




}
