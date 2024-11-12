package com.example.github_trending_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class GithubRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 500)
    private String name;
    
    @Column(length = 1000)
    private String url;
    
    @Column(length = 2000)
    private String description;
    
    @Column(length = 100)
    private String language;
    
    private Integer stars;
    private Integer forks;
    private LocalDateTime crawledAt;
}