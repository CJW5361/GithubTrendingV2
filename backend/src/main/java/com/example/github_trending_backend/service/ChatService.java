package com.example.github_trending_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class ChatService {
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    
    public ChatService() {
        this.restTemplate = new RestTemplate();
    }
    
    public String getRepoAnalysis(String repoName, String readme) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        
        List<Map<String, Object>> messages = new ArrayList<>();
        
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", 
            "GitHub 저장소 분석 전문가로서, 다음 형식으로 간단명료하게 분석 결과를 제공해주세요:\n\n" +
            "📌 프로젝트 목적\n" +
            "- 한 줄로 핵심 설명\n\n" +
            "💡 주요 기능\n" +
            "- 중요 기능 3-4개 bullet points\n\n" +
            "🛠 기술 스택\n" +
            "- 사용된 주요 기술들 나열\n\n" +
            "✨ 특징\n" +
            "- 차별화된 특징 2-3개\n\n" +
            "응답은 반드시 이 형식을 따르고, 각 섹션은 2-3줄을 넘지 않도록 해주세요."
        );
        messages.add(systemMessage);
        
        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", String.format(
            "GitHub 저장소 %s의 README 내용입니다:\n\n%s", 
            repoName, readme));
        messages.add(userMessage);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);
        
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> messageResponse = (Map<String, Object>) firstChoice.get("message");
            return (String) messageResponse.get("content");
            
        } catch (Exception e) {
            e.printStackTrace();
            return "분석 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
} 