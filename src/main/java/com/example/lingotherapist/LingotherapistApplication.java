package com.example.lingotherapist;

import com.example.lingotherapist.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LingotherapistApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingotherapistApplication.class, args);
    }
}

@RestController
class ChatController {

    @Autowired
    private AiService aiService;

    @GetMapping("/")
    public String home() {
        return "<h1>LingoTherapist</h1><p>Chat with AI: <a href='/chat.html'>Open Chat</a></p>";
    }

    @GetMapping("/api/chat")
    public String chat(@RequestParam String message) {
        if (message == null || message.isBlank()) {
            return "Please say something...";
        }
        return aiService.getTherapyResponse(message);
    }
}