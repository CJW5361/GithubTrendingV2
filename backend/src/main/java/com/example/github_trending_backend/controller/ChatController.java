package com.example.github_trending_backend.controller;

import com.example.github_trending_backend.dto.QuestionRequest;
import com.example.github_trending_backend.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, String>> analyzeRepo(@RequestBody Map<String, String> request) {
        String analysis = chatService.getRepoAnalysis(request.get("repoName"), request.get("readme"));
        return ResponseEntity.ok(Map.of("analysis", analysis));
    }

    @PostMapping("/question")
    public ResponseEntity<Map<String, String>> answerQuestion(@RequestBody QuestionRequest request) {
        String response = chatService.getAnswer(request.getRepoName(), request.getQuestion());
        return ResponseEntity.ok(Map.of("response", response));
    }
} 