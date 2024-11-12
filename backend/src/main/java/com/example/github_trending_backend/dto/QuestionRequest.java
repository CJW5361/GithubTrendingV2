package com.example.github_trending_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private String repoName;
    private String question;
} 