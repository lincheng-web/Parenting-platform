package com.parenting.boot.controller;

import com.parenting.core.service.RagService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
@CrossOrigin(origins = "*")
public class RagController {

    private static final Logger log = LoggerFactory.getLogger(RagController.class);

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, Object> request) {
        String question = (String) request.get("question");
        Boolean useRag = (Boolean) request.getOrDefault("useRag", false);
        
        log.info("收到问题: {}, useRag: {}", question, useRag);

        Map<String, Object> response = new HashMap<>();
        
        try {
            String answer;
            if (Boolean.TRUE.equals(useRag)) {
                answer = ragService.answerQuestion(question);
            } else {
                answer = ragService.answerQuestion(question);
            }
            
            response.put("success", true);
            response.put("answer", answer);
            response.put("sources", new String[]{"DeepSeek API"});
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("处理问题失败", e);
            response.put("success", false);
            response.put("answer", "抱歉，服务暂时不可用，请稍后重试。错误信息：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
