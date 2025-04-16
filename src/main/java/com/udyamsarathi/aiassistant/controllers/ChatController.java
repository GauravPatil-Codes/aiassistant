package com.udyamsarathi.aiassistant.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // for user another query
    @PostMapping("/ask")
    public ResponseEntity<?> askAI(@RequestParam String question) {
        try {
            // Direct call to AI without any filtering or processing
            ChatResponse aiResponse = chatClient.prompt()
                    .user(question)
                    .call()
                    .chatResponse();
            
            return ResponseEntity.ok(aiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get AI response: " + e.getMessage());
        }
    }

    // for business related query
}















// package com.udyamsarathi.aiassistant.controllers;

// public class ChatController {

// }
