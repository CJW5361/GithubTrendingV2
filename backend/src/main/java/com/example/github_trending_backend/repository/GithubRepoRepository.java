package com.example.github_trending_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.github_trending_backend.entity.GithubRepo;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {
    List<GithubRepo> findByCrawledAtBetween(LocalDateTime start, LocalDateTime end);
}