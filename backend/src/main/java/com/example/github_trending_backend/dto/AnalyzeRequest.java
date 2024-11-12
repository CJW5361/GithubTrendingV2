package com.example.github_trending_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyzeRequest {
    private String repoName;
    private String readme;
} 