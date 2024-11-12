package com.example.github_trending_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.github_trending_backend.service.ChatService;
import com.example.github_trending_backend.dto.AnalyzeRequest;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    
    @PostMapping("/analyze")
    public String analyzeRepo(@RequestBody AnalyzeRequest request) {
        return chatService.getRepoAnalysis(request.getRepoName(), request.getReadme());
    }
} 