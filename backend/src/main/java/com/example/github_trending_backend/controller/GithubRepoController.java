package com.example.github_trending_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.github_trending_backend.entity.GithubRepo;
import com.example.github_trending_backend.service.GithubRepoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/repos")
@RequiredArgsConstructor
public class GithubRepoController {
    
    private final GithubRepoService githubRepoService;
    
    @GetMapping("/trending")
    public List<GithubRepo> getTrending() {
        return githubRepoService.getTodaysTrending();
    }
    
    @PostMapping
    public GithubRepo saveRepo(@RequestBody GithubRepo repo) {
        return githubRepoService.saveRepo(repo);
    }
}