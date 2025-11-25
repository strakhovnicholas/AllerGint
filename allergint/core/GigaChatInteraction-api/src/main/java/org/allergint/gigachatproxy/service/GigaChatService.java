package org.allergint.gigachatproxy.service;

import chat.giga.client.GigaChatClient;
import chat.giga.model.ModelName;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.ChatMessageRole;
import chat.giga.model.completion.CompletionRequest;
import org.allergint.gigachatproxy.dto.GigaChatMetricsRequest;
import org.allergint.gigachatproxy.dto.GigaChatResponse;
import org.allergint.gigachatproxy.dto.GigaChatUserRequest;
import org.springframework.stereotype.Service;

@Service
public class GigaChatService implements AiService {
    private final GigaChatClient client;

    public GigaChatService(GigaChatClient client) {
        this.client = client;
    }

    public GigaChatResponse buildUserPrompt(GigaChatUserRequest request) {
        String aiAnswer = this.sendPrompt(request.getMessage());
        return GigaChatResponse.builder().answer(aiAnswer).clientId(request.getClientId()).build();
    }

    public GigaChatResponse buildMetricsPrompt(GigaChatMetricsRequest request) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Анализ состояния клиента за день.\n");
        prompt.append("Метрики:\n");

        for (String metric : request.getMetrics()) {
            prompt.append(metric).append("\n");
        }
        prompt.append("Выдай рекомендации и возможные риски.");

        String aiAnswer = this.sendPrompt(String.valueOf(prompt));
        return GigaChatResponse.builder().answer(aiAnswer).clientId(request.getClientId()).build();
    }

    public String sendPrompt(String request) {
        var response = client.completions(
                CompletionRequest.builder()
                        .model(ModelName.GIGA_CHAT_PRO)
                        .message(ChatMessage.builder()
                                .content(request)
                                .role(ChatMessageRole.USER)
                                .build())
                        .build());

        return response.choices().getFirst().message().content();
    }

}

