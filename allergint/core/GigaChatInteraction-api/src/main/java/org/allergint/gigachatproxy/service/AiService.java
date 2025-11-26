package org.allergint.gigachatproxy.service;

import org.allergint.gigachatproxy.dto.GigaChatMetricsRequest;
import org.allergint.gigachatproxy.dto.GigaChatResponse;
import org.allergint.gigachatproxy.dto.GigaChatUserRequest;

public interface AiService {
    GigaChatResponse buildUserPrompt(GigaChatUserRequest message);
    GigaChatResponse buildMetricsPrompt(GigaChatMetricsRequest message);
}
