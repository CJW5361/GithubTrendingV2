package com.example.github_trending_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.github_trending_backend.entity.GithubRepo;
import com.example.github_trending_backend.repository.GithubRepoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubRepoService {
    
    private final GithubRepoRepository githubRepoRepository;
    
    public List<GithubRepo> getTodaysTrending() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now();
        return githubRepoRepository.findByCrawledAtBetween(start, end);
    }
    
    public GithubRepo saveRepo(GithubRepo repo) {
        repo.setCrawledAt(LocalDateTime.now());
        return githubRepoRepository.save(repo);
    }
}