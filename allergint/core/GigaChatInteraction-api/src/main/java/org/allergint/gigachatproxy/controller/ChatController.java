package org.allergint.gigachatproxy.controller;

import org.allergint.gigachatproxy.dto.GigaChatMetricsRequest;
import org.allergint.gigachatproxy.dto.GigaChatUserRequest;
import org.allergint.gigachatproxy.dto.GigaChatResponse;
import org.allergint.gigachatproxy.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class ChatController {
    private final AiService aiService;

    public ChatController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/metrics")
    public GigaChatResponse chat(@RequestBody GigaChatMetricsRequest request) {
        return this.aiService.buildMetricsPrompt(request);
    }

    @PostMapping("/user")
    public GigaChatResponse chat(@RequestBody GigaChatUserRequest request) {
        return this.aiService.buildUserPrompt(request);
    }
}
