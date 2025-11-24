package ru.allergint.weatherservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.allergint.weatherservice.dto.GigaChatMetricsRequest;
import ru.allergint.weatherservice.dto.GigaChatResponse;

@FeignClient(name = "allergy-gigachat-client", path = "/ai")
public interface GigaChatClient {
    @PostMapping("/metrics")
    GigaChatResponse getRecommendations(@RequestBody GigaChatMetricsRequest request);
}

